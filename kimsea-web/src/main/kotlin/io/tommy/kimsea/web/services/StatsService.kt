package io.tommy.kimsea.web.services

import io.tommy.kimsea.web.dto.StatDTO
import io.tommy.kimsea.web.entity.domain.Asset
import io.tommy.kimsea.web.entity.repository.AssetHistoryRepository
import io.tommy.kimsea.web.entity.repository.AssetRepository
import io.tommy.kimsea.web.entity.repository.UserRepository
import io.tommy.kimsea.web.enums.AssetHistoryStatusEnum
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class StatsService(
    val userRepository: UserRepository,
    val assetRepository: AssetRepository,
    val assetHistoryRepository: AssetHistoryRepository
) {
    var statsUsers = listOf<StatDTO.UserDTO>()
    var statsCreators = listOf<StatDTO.CreatorDTO>()

    fun doStatus() {
        renewStatsUsers()
        renewStatsCreators()
    }

    fun renewStatsUsers() {
        val _statsUsers = userRepository.findAll().toList().map {
            StatDTO.UserDTO(
                it, assetRepository.countAllByCreator(it), assetHistoryRepository.sumSellCountByUserId(AssetHistoryStatusEnum.SELL, it) ?: 0, assetHistoryRepository.sumSellPriceByUserId(AssetHistoryStatusEnum.SELL, it)  ?: 0
            )
        }.toList()

        Collections.sort(_statsUsers) { o1, o2 -> if (o1.totalAssetCnt > o2.totalAssetCnt) -1 else if (o1.totalAssetCnt < o2.totalAssetCnt) 1 else 0 }
        statsUsers = _statsUsers
    }

    fun renewStatsCreators() {
        val _statsCreators = userRepository.findAll().toList().map {
            StatDTO.CreatorDTO(
                it, assetRepository.countAllByOwner(it), assetHistoryRepository.sumSellCountByUserId(AssetHistoryStatusEnum.SELL, it) ?: 0, assetHistoryRepository.sumSellPriceByUserId(AssetHistoryStatusEnum.SELL, it)  ?: 0
            )
        }.toList()

        Collections.sort(_statsCreators) { o1, o2 -> if (o1.totalSellPrice > o2.totalSellPrice) -1 else if (o1.totalSellPrice < o2.totalSellPrice) 1 else 0 }
        statsCreators = _statsCreators
    }

    fun getStatsUsers(pageNo:Int, pageSize:Int) : List<StatDTO.UserDTO> {
        if (statsUsers.size < pageNo*pageSize) {
            return arrayListOf()
        }
        var lastOffset = (pageNo+1)*pageSize - 1
        if (statsUsers.size < lastOffset) {
            lastOffset = statsUsers.size
        }
        return statsUsers.subList(pageNo*pageSize, lastOffset)
    }

    fun getStatsCreators(pageNo:Int, pageSize:Int) : List<StatDTO.CreatorDTO> {
        if (statsUsers.size < pageNo*pageSize) {
            return arrayListOf()
        }
        var lastOffset = (pageNo+1)*pageSize - 1
        if (statsUsers.size < lastOffset) {
            lastOffset = statsUsers.size
        }
        return statsCreators.subList(pageNo*pageSize, lastOffset)
    }

    fun getStatsAssets(pageNo:Int, pageSize:Int) : List<Asset> {
        return assetRepository.findAllByDelDttmIsNullOrderByPriceDesc(PageRequest.of(pageNo, pageSize)).toList()
    }
}