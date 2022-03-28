package io.tommy.kimsea.web.services.asset

import com.fasterxml.jackson.databind.ObjectMapper
import io.tommy.kimsea.web.dto.StatisticsDTO
import io.tommy.kimsea.web.entity.domain.Asset
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.entity.repository.AssetRepository
import io.tommy.kimsea.web.entity.repository.OrderRepository
import io.tommy.kimsea.web.entity.repository.TransactionRepository
import io.tommy.kimsea.web.entity.repository.UserRepository
import io.tommy.kimsea.web.enums.*
import io.tommy.kimsea.web.exceptions.WebException
import io.tommy.kimsea.web.utils.logger
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AssetService(
    private val assetRepository:AssetRepository,
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) {
    val log = logger()
    val objectMapper = ObjectMapper()

    fun getTopAssetsStatistics() : StatisticsDTO.TopAssetsDTO {
        return StatisticsDTO.TopAssetsDTO(
            assetRepository.countAllByDelDttmIsNull(),
            assetRepository.countAllByDelDttmIsNullAndCategory(CategoryEnum.VIDEO),
            assetRepository.countAllByDelDttmIsNullAndCategory(CategoryEnum.MUSIC),
            assetRepository.countAllByDelDttmIsNullAndStatus(AssetStatusEnum.SELL),
            orderRepository.sumByStatus(OrderStatusEnum.COMPLETED_SELL).orElse(0),
        )
    }

    fun getPopularAssets(user:User?, query:String?, pageNo:Int, pageSize:Int) : List<Asset> {
        var assets:List<Asset>? = null
        if (query.isNullOrBlank()) {
            assets = assetRepository.findAllByStatusNotInOrderByPriceDesc(listOf(AssetStatusEnum.WITHDRAWING, AssetStatusEnum.WITHDRAW, AssetStatusEnum.DELETE), PageRequest.of(pageNo, pageSize))
        } else {
            assets = assetRepository.findAllByNameContainingAndStatusNotInOrderByPriceDesc(query, listOf(AssetStatusEnum.WITHDRAWING, AssetStatusEnum.WITHDRAW, AssetStatusEnum.DELETE), PageRequest.of(pageNo, pageSize))
        }
        user?.let {
            assets.forEach { asset ->
                asset.apply {
                    buyOrder = orderRepository.findByUserIdAndAssetIdAndStatusOrderByPriceDesc(it.id!!, id!!, OrderStatusEnum.BUY)
                    sellOrder = orderRepository.findFirstByAssetIdAndStatus(id!!, OrderStatusEnum.SELL)
                }
            }
        }
        return assets
    }

    fun getRecentlyAssets(user:User?, query:String?, pageNo:Int, pageSize:Int) : List<Asset> {
        var assets:List<Asset>? = null
        if (query.isNullOrBlank()) {
            assets = assetRepository.findAllByStatusNotInOrderByRegDttmDesc(listOf(AssetStatusEnum.WITHDRAWING, AssetStatusEnum.WITHDRAW, AssetStatusEnum.DELETE), PageRequest.of(pageNo, pageSize))
        } else {
            assets = assetRepository.findAllByNameContainingAndStatusNotInOrderByRegDttmDesc(query, listOf(AssetStatusEnum.WITHDRAWING, AssetStatusEnum.WITHDRAW, AssetStatusEnum.DELETE), PageRequest.of(pageNo, pageSize))
        }
        user?.let {
            assets.forEach { asset ->
                asset.apply {
                    buyOrder = orderRepository.findByUserIdAndAssetIdAndStatusOrderByPriceDesc(it.id!!, id!!, OrderStatusEnum.BUY)
                    sellOrder = orderRepository.findFirstByAssetIdAndStatus(id!!, OrderStatusEnum.SELL)
                }
            }
        }
        return assets
    }

    fun getAssets(user:User?, query:String?, pageNo:Int, pageSize:Int) : List<Asset> {
        var assets:List<Asset>? = null
        if (query.isNullOrBlank()) {
            assets = assetRepository.findAllByOrderByRegDttmDesc(PageRequest.of(pageNo, pageSize))
        } else {
            assets = assetRepository.findAllByNameContainingOrderByRegDttmDesc(query, PageRequest.of(pageNo, pageSize))
        }
        user?.let {
            assets.forEach { asset ->
                asset.apply {
                    buyOrder = orderRepository.findByUserIdAndAssetIdAndStatusOrderByPriceDesc(it.id!!, id!!, OrderStatusEnum.BUY)
                    sellOrder = orderRepository.findFirstByAssetIdAndStatus(id!!, OrderStatusEnum.SELL)
                }
            }
        }
        return assets;
    }

    fun getMyAssetsByStatus(user:User, status: AssetStatusEnum, query:String?, pageNo:Int, pageSize:Int) : List<Asset> {
        val pageRequest = PageRequest.of(pageNo, pageSize)
        return if (status == AssetStatusEnum.ALL) {
            var assets:List<Asset>
            if (query.isNullOrBlank()) {
                assets = assetRepository.findAllByOwnerOrderByRegDttmDesc(user, pageRequest)
            } else {
                assets = assetRepository.findAllByNameContainingAndOwnerOrderByRegDttmDesc(query, user, pageRequest)
            }
            return assets.map{ asset ->
                asset.apply {
                    buyOrder = orderRepository.findByUserIdAndAssetIdAndStatusOrderByPriceDesc(user.id!!, id!!, OrderStatusEnum.BUY)
                    sellOrder = orderRepository.findFirstByAssetIdAndStatus(id!!, OrderStatusEnum.SELL)
                }
            }.toList()
        } else if (status == AssetStatusEnum.SELL) {
            var assets:List<Asset>
            if (query.isNullOrBlank()) {
                assets = assetRepository.findAllByOwnerAndBidEndDttmIsNotNullOrderByRegDttmDesc(user, pageRequest)
            } else {
                assets = assetRepository.findAllByNameContainingAndOwnerAndBidEndDttmIsNotNullOrderByRegDttmDesc(query, user, pageRequest)
            }

            return assets.map{ asset ->
                asset.apply {
                    buyOrder = orderRepository.findByUserIdAndAssetIdAndStatusOrderByPriceDesc(user.id!!, id!!,OrderStatusEnum.BUY)
                    sellOrder = orderRepository.findFirstByAssetIdAndStatus(id!!, OrderStatusEnum.SELL)
                }
            }.filter { asset -> asset.sellOrder != null }.toList()
        } else if (status == AssetStatusEnum.BUY) {
           return orderRepository.findByUserIdAndStatus(user.id!!, OrderStatusEnum.BUY).map {
                buyOrder ->
                    buyOrder.run {
                        assetRepository.findById(assetId).get().apply {
                            this.buyOrder = buyOrder
                            sellOrder = orderRepository.findFirstByAssetIdAndStatus(userId, OrderStatusEnum.SELL)
                        }
                    }
            }.toList()
        } else if (status == AssetStatusEnum.WITHDRAWING) {
            var assets:List<Asset>
            if (query.isNullOrBlank()) {
                assets = assetRepository.findAllByOwnerAndStatusOrderByRegDttmDesc(user, status, pageRequest)
            } else {
                assets = assetRepository.findAllByNameContainingAndOwnerAndStatusOrderByRegDttmDesc(query, user, status, pageRequest)
            }
            return assets.map{ asset ->
                asset.apply {
                    buyOrder = orderRepository.findByUserIdAndAssetIdAndStatusOrderByPriceDesc(user.id!!, id!!, OrderStatusEnum.BUY)
                    sellOrder = orderRepository.findFirstByAssetIdAndStatus(id!!, OrderStatusEnum.SELL)
                    withdrawTx = transactionRepository.findFirstByCoinAndCategoryAndAssetIdAndStatusIn(CoinEnum.KIMSEA, TransactionCategoryEnum.WITHDRAW, id!!, listOf(TransactionStatusEnum.PENDING, TransactionStatusEnum.PROCESS))
                }
            }.toList()
        } else if (status == AssetStatusEnum.WITHDRAW) {
            var assets:List<Asset>
            if (query.isNullOrBlank()) {
                assets = assetRepository.findAllByOwnerAndStatusOrderByRegDttmDesc(user, status, pageRequest)
            } else {
                assets = assetRepository.findAllByNameContainingAndOwnerAndStatusOrderByRegDttmDesc(query, user, status, pageRequest)
            }
            return assets.map{ asset ->
                asset.apply {
                    buyOrder = orderRepository.findByUserIdAndAssetIdAndStatusOrderByPriceDesc(user.id!!, id!!, OrderStatusEnum.BUY)
                    sellOrder = orderRepository.findFirstByAssetIdAndStatus(id!!, OrderStatusEnum.SELL)
                    withdrawTx = transactionRepository.findFirstByCoinAndCategoryAndAssetIdAndStatusIn(CoinEnum.KIMSEA, TransactionCategoryEnum.WITHDRAW, id!!, listOf(TransactionStatusEnum.COMPLETED))
                }
            }.toList()
        } else {
            var assets:List<Asset>
            if (query.isNullOrBlank()) {
                assets = assetRepository.findAllByOwnerOrderByRegDttmDesc(user, pageRequest)
            } else {
                assets = assetRepository.findAllByNameContainingAndOwnerOrderByRegDttmDesc(query, user, pageRequest)
            }
            return assets
        }
    }

    fun getAssetsByOwner(user:User?, userId:Long, query:String?, pageNo:Int, pageSize:Int) : List<Asset> {
        val pageRequest = PageRequest.of(pageNo, pageSize)
        val owner = userRepository.findById(userId).orElseThrow { WebException(CodeEnum.FAIL) }
        var assets:List<Asset>
        if (query.isNullOrBlank()) {
            assets = assetRepository.findAllByOwnerOrderByRegDttmDesc(owner, pageRequest)
        } else {
            assets = assetRepository.findAllByNameContainingAndOwnerOrderByRegDttmDesc(query, owner, pageRequest)
        }
        user?.let {
            assets.forEach { asset ->
                asset.apply {
                    buyOrder = orderRepository.findByUserIdAndAssetIdAndStatusOrderByPriceDesc(it.id!!, id!!, OrderStatusEnum.BUY)
                    sellOrder = orderRepository.findFirstByAssetIdAndStatus(id!!, OrderStatusEnum.SELL)
                }
            }
        }
        return assets
    }

    fun getAssetsByCreator(user:User?, userId:Long, query:String?, pageNo:Int, pageSize:Int) : List<Asset> {
        val pageRequest = PageRequest.of(pageNo, pageSize)
        val creator = userRepository.findById(userId).orElseThrow { WebException(CodeEnum.FAIL) }
        var assets:List<Asset>
        if (query.isNullOrBlank()) {
            assets = assetRepository.findAllByCreatorOrderByRegDttmDesc(creator, pageRequest)
        } else {
            assets = assetRepository.findAllByNameContainingAndCreatorOrderByRegDttmDesc(query, creator, pageRequest)
        }

        user?.let {
            assets.forEach { asset ->
                asset.apply {
                    buyOrder = orderRepository.findByUserIdAndAssetIdAndStatusOrderByPriceDesc(it.id!!, id!!, OrderStatusEnum.BUY)
                    sellOrder = orderRepository.findFirstByAssetIdAndStatus(id!!, OrderStatusEnum.SELL)
                }
            }
        }
        return assets
    }

    fun getAssetsByCategory(user:User?, category:CategoryEnum, query:String?, pageNo:Int, pageSize:Int) : List<Asset> {
        val statuses = listOf(AssetStatusEnum.WITHDRAWING, AssetStatusEnum.WITHDRAW, AssetStatusEnum.DELETE)
        val pageRequest = PageRequest.of(pageNo, pageSize)
        return if (category == CategoryEnum.ALL) {
            var assets: List<Asset>
            if (query.isNullOrBlank()) {
                assets = assetRepository.findAllByStatusNotInOrderByRegDttmDesc(statuses, pageRequest)
            } else {
                assets = assetRepository.findAllByNameContainingAndStatusNotInOrderByRegDttmDesc(query, statuses, pageRequest)
            }

            user?.let {
                assets.forEach { asset ->
                    asset.apply {
                        buyOrder = orderRepository.findByUserIdAndAssetIdAndStatusOrderByPriceDesc(it.id!!, id!!, OrderStatusEnum.BUY)
                        sellOrder = orderRepository.findFirstByAssetIdAndStatus(id!!, OrderStatusEnum.SELL)
                    }
                }
            }
            return assets
        } else {
            var assets: List<Asset>
            if (query.isNullOrBlank()) {
                assets = assetRepository.findAllByStatusNotInAndCategoryOrderByRegDttmDesc(statuses, category, pageRequest)
            } else {
                assets = assetRepository.findAllByNameContainingAndStatusNotInAndCategoryOrderByRegDttmDesc(query, statuses, category, pageRequest)
            }

            user?.let {
                assets.forEach { asset ->
                    asset.apply {
                        buyOrder = orderRepository.findByUserIdAndAssetIdAndStatusOrderByPriceDesc(it.id!!, id!!, OrderStatusEnum.BUY)
                        sellOrder = orderRepository.findFirstByAssetIdAndStatus(id!!, OrderStatusEnum.SELL)
                    }
                }
            }
            return assets
        }
    }

    fun getAssetById(user:User?, id:Long) : Asset {
        val asset = assetRepository.findByIdOrNull(id)!!
        user?.let {
            asset.apply {
                buyOrder = orderRepository.findByUserIdAndAssetIdAndStatusOrderByPriceDesc(it.id!!, id, OrderStatusEnum.BUY)
                sellOrder = orderRepository.findFirstByAssetIdAndStatus(id, OrderStatusEnum.SELL)
            }
        }
        return asset
    }
}