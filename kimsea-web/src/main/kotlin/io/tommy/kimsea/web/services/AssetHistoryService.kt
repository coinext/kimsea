package io.tommy.kimsea.web.services

import io.tommy.kimsea.web.entity.domain.AssetHistory
import io.tommy.kimsea.web.entity.repository.AssetHistoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class AssetHistoryService(
    private val assetHistoryRepository: AssetHistoryRepository
) {

    fun getOrderHistoriesByAssetId(assetId:Long, pageNo:Int, pageSize:Int) : List<AssetHistory> {
        return assetHistoryRepository.findByAssetIdOrderByRegDttmAsc(assetId, PageRequest.of(pageNo, pageSize))
    }
}