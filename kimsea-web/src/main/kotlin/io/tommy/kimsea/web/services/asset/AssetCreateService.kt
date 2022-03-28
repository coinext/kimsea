package io.tommy.kimsea.web.services.asset

import com.fasterxml.jackson.databind.ObjectMapper
import io.tommy.kimsea.web.dto.AssetDTO
import io.tommy.kimsea.web.dto.token.NftMetadataDTO
import io.tommy.kimsea.web.entity.domain.Asset
import io.tommy.kimsea.web.entity.domain.AssetHistory
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.entity.repository.AssetHistoryRepository
import io.tommy.kimsea.web.entity.repository.AssetRepository
import io.tommy.kimsea.web.enums.AssetHistoryStatusEnum
import io.tommy.kimsea.web.enums.AssetMimeEnum
import io.tommy.kimsea.web.enums.AssetStatusEnum
import io.tommy.kimsea.web.providers.IpfsProvider
import io.tommy.kimsea.web.utils.AsyncDispatcher
import io.tommy.kimsea.web.utils.logger
import kotlinx.coroutines.awaitAll
import org.apache.tika.Tika
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AssetCreateService(
    private val assetRepository:AssetRepository,
    private val assetHistoryRepository:AssetHistoryRepository,
    private val ipfsProvider: IpfsProvider
) {
    val log = logger()
    val objectMapper = ObjectMapper()

    suspend fun createAsset(user:User, reqCreateAssetDTO: AssetDTO.ReqCreateAssetDTO) : Asset {
        log.info("---createAsset")
        val mime = Tika().detect(reqCreateAssetDTO.attachFile.bytes)
        log.info("attachFileHash contentType =======> ${mime}")

        val ipfsAttachFiles =  listOf(
            AsyncDispatcher.asAsync { ipfsProvider.add(user, reqCreateAssetDTO.prevImageFile, false) },
            AsyncDispatcher.asAsync {ipfsProvider.add(user, reqCreateAssetDTO.imageFile)},
            AsyncDispatcher.asAsync {ipfsProvider.add(user, reqCreateAssetDTO.attachFile, false)}).awaitAll()

        val asset = ipfsAttachFiles.run {
            val prevImageFileName = this[0].first
            val prevImageFileHash = this[0].second
            log.info("ipfs prevImageFileHash =======> https://ipfs.io/ipfs/${prevImageFileHash}")

            val imageFileHash = this[1].second
            log.info("ipfs imageFileHash =======> https://ipfs.io/ipfs/${imageFileHash}")

            val prevAttachFileName = this[2].first
            val attachFileHash = this[2].second
            log.info("ipfs attachFileHash =======> https://ipfs.io/ipfs/${attachFileHash}")

            var assetMime = AssetMimeEnum.IMAGE
            var fileType = "image"
            if (mime.contains("text/plain")
                || mime.contains("application/octet-stream")) {
                assetMime = AssetMimeEnum.MODEL
                fileType = "model"
            } else if (mime.contains("video/")) {
                assetMime = AssetMimeEnum.VIDEO
                fileType = "video"
            } else if (mime.contains("audio/")) {
                assetMime = AssetMimeEnum.AUDIO
                fileType = "audio"
            } else if (mime.contains("image/")) {
                assetMime = AssetMimeEnum.IMAGE
                fileType = "image"
            }


            Asset(
                id = null,
                creator = user,
                owner = user,
                category = reqCreateAssetDTO.category,
                mime = assetMime,
                status = AssetStatusEnum.PLACED,
                name = reqCreateAssetDTO.name,
                description = reqCreateAssetDTO.description,
                imgUrl = ipfsProvider.getLinkUrl(imageFileHash),
                prevImgUrl = "/file/image/$prevImageFileName",
                thumbImgUrl = ipfsProvider.getLinkUrl(prevImageFileHash),
                attachFileUrl = ipfsProvider.getLinkUrl(attachFileHash),
                prevAttachFileUrl = "/file/${fileType}/$prevAttachFileName",
                attrs = reqCreateAssetDTO.attrs,
                regDttm = LocalDateTime.now(),
                bidEndDttm = null,
                delDttm = null,
                mintDttm = null
            )
        }

        return asset.run {
            val _asset = assetRepository.save(this)

            val orgAttrs = objectMapper.readValue(asset.attrs, object : com.fasterxml.jackson.core.type.TypeReference<List<NftMetadataDTO.Attribute>>() {})
            val attrs = orgAttrs.filter { a -> !a.trait_type.isNullOrBlank() && !a.value.isNullOrBlank() }.toList()

            val metadataDTO = NftMetadataDTO(
                asset.description,
                "https://kimsea.me/home#/asset/${_asset.id}",
                _asset.attachFileUrl,
                _asset.name,
                attrs
            )
            val metadataUrl = ipfsProvider.add(objectMapper.writeValueAsString(metadataDTO))
            log.info("ipfs metadataUrl =======> ${metadataUrl}")

            this.metadataUrl = metadataUrl
            assetRepository.save(this)

            assetHistoryRepository.save(AssetHistory(
                assetId = asset.id!!,
                status = AssetHistoryStatusEnum.CREATE,
                from = user,
                to = user,
                price = 0,
                fee = 0,
            ))
            _asset
        }
    }
}