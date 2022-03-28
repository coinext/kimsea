package io.tommy.kimsea.web.entity.repository

import io.tommy.kimsea.web.entity.domain.Asset
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.enums.AssetStatusEnum
import io.tommy.kimsea.web.enums.CategoryEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AssetRepository : CrudRepository<Asset, Long> {

    fun findAllByDelDttmIsNullOrderByPriceDesc(pageable: Pageable) : List<Asset>
    fun findAll(pageable: Pageable) : List<Asset>

    fun countAllByCreator(creator:User) : Long
    fun countAllByOwner(owner:User) : Long

    fun countAllByDelDttmIsNull() : Long
    fun countAllByDelDttmIsNullAndCategory(category: CategoryEnum) : Long
    fun countAllByDelDttmIsNullAndStatus(status: AssetStatusEnum) : Long

    fun findAllByOwnerAndBidEndDttmIsNotNullOrderByRegDttmDesc(owner:User, pageable:Pageable):List<Asset>
    fun findAllByNameContainingAndOwnerAndBidEndDttmIsNotNullOrderByRegDttmDesc(name:String, owner:User, pageable:Pageable):List<Asset>

    fun findAllByOwnerOrderByRegDttmDesc(owner:User, pageable:Pageable):List<Asset>
    fun findAllByNameContainingAndOwnerOrderByRegDttmDesc(name:String, owner:User, pageable:Pageable):List<Asset>

    fun findAllByOwnerAndStatusOrderByRegDttmDesc(owner:User, status:AssetStatusEnum, pageable:Pageable):List<Asset>
    fun findAllByNameContainingAndOwnerAndStatusOrderByRegDttmDesc(name:String, owner:User, status:AssetStatusEnum, pageable:Pageable):List<Asset>

    fun findAllByCreatorOrderByRegDttmDesc(creator:User, pageable:Pageable):List<Asset>
    fun findAllByNameContainingAndCreatorOrderByRegDttmDesc(name:String, creator:User, pageable:Pageable):List<Asset>

    fun findAllByStatusNotInOrderByRegDttmDesc(statuses:List<AssetStatusEnum>, pageable:Pageable):List<Asset>
    fun findAllByNameContainingAndStatusNotInOrderByRegDttmDesc(name:String, statuses:List<AssetStatusEnum>, pageable:Pageable):List<Asset>

    fun findAllByStatusNotInOrderByPriceDesc(statuses:List<AssetStatusEnum>, pageable:Pageable):List<Asset>
    fun findAllByNameContainingAndStatusNotInOrderByPriceDesc(name:String, statuses:List<AssetStatusEnum>, pageable:Pageable):List<Asset>

    fun findAllByOrderByRegDttmDesc(pageable:Pageable):List<Asset>
    fun findAllByNameContainingOrderByRegDttmDesc(name:String,pageable:Pageable):List<Asset>

    fun findByIdAndOwner(id:Long, owner:User) : Asset?

    fun findAllByStatusNotInAndCategoryOrderByRegDttmDesc(statuses:List<AssetStatusEnum>,category:CategoryEnum, pageable:Pageable):List<Asset>
    fun findAllByNameContainingAndStatusNotInAndCategoryOrderByRegDttmDesc(name:String, statuses:List<AssetStatusEnum>,category:CategoryEnum, pageable:Pageable):List<Asset>

    fun findAllByCategoryOrderByRegDttmDesc(category:CategoryEnum, pageable:Pageable):List<Asset>
}