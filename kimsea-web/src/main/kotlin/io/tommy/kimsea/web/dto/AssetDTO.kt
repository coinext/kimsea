package io.tommy.kimsea.web.dto

import io.tommy.kimsea.web.enums.CategoryEnum
import io.tommy.kimsea.web.enums.NetworkChainEnum
import org.springframework.web.multipart.MultipartFile

class AssetDTO {
    data class ReqWithdrawAssetDTO (
        val assetId:Long,
        val toAddress:String
    )

    data class ReqMintAssetDTO (
        val assetId:Long
    )

    data class ReqCreateAssetDTO (
        val prevImageFile:MultipartFile,
        val imageFile:MultipartFile,
        val attachFile:MultipartFile,
        val networkChain:NetworkChainEnum,
        val category:CategoryEnum,
        val name:String,
        val description:String,
        val attrs:String,
    )

    data class ReqSellAssetDTO (
        val assetId:Long,
        val price:Long,
    )

    data class ReqModifySellAssetDTO (
        val assetId:Long,
        val price:Long,
    )


    data class ReqCancelSellAssetDTO (
        val assetId:Long,
    )

    data class ReqBuyAssetDTO (
        val assetId:Long,
        val price:Long,
    )


    data class ReqModifyBuyAssetDTO (
        val assetId:Long,
        val price:Long,
    )

    data class ReqCancelBuyAssetDTO (
        val assetId:Long,
    )
}