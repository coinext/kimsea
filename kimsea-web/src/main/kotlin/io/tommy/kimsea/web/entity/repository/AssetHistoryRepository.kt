package io.tommy.kimsea.web.entity.repository

import io.tommy.kimsea.web.entity.domain.AssetHistory
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.enums.AssetHistoryStatusEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AssetHistoryRepository : CrudRepository<AssetHistory, Long> {
    fun findByAssetIdOrderByRegDttmAsc(assetId:Long, pageable: Pageable) : List<AssetHistory>

    @Query("select sum(a.price) from AssetHistory a where a.status = :status and a.from = :from")
    fun sumSellPriceByUserId(status: AssetHistoryStatusEnum, from: User) : Long?

    @Query("select count(a) from AssetHistory a where a.status = :status and a.from = :from")
    fun sumSellCountByUserId(status: AssetHistoryStatusEnum, from: User) : Long?
}