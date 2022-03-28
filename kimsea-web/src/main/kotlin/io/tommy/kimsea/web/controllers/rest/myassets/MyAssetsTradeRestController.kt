package io.tommy.kimsea.web.controllers.rest.myassets

import io.tommy.kimsea.web.dto.AssetDTO
import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.Order
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.providers.EthereumProvider
import io.tommy.kimsea.web.services.UserService
import io.tommy.kimsea.web.services.asset.AssetTradeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/myassets")
class MyAssetsTradeRestController(
    private val userService: UserService,
    private val assetTradeService: AssetTradeService,
    private val ethereumProvider: EthereumProvider
) {

    //**API SUPPORT
    @PostMapping("/sellAsset")
    fun sellAsset(user:User?, @RequestHeader("api-key") token:String?, @RequestBody reqSellAssetDTO: AssetDTO.ReqSellAssetDTO) : Response<Order> {
        val _user = userService.validateUserForAPI(user, token)
        //발행 출금된 NFT인지 확인.
        ethereumProvider.availableAsset(reqSellAssetDTO.assetId)
        return Response(assetTradeService.sellAsset(_user, reqSellAssetDTO))
    }

    //**API SUPPORT
    @PostMapping("/cancelSellAsset")
    fun cancelSellAsset(user:User?, @RequestHeader("api-key") token:String?, @RequestBody reqCancelSellAssetDTO: AssetDTO.ReqCancelSellAssetDTO) : Response<Order> {
        val _user = userService.validateUserForAPI(user, token)
        return Response(assetTradeService.cancelSellAsset(_user, reqCancelSellAssetDTO))
    }

    //**API SUPPORT
    @PostMapping("/modifySellAsset")
    fun modifySellAsset(user:User?, @RequestHeader("api-key") token:String?, @RequestBody reqModifySellAssetDTO: AssetDTO.ReqModifySellAssetDTO) : Response<Order> {
        val _user = userService.validateUserForAPI(user, token)
        return Response(assetTradeService.modifySellAsset(_user, reqModifySellAssetDTO))
    }

    //**API SUPPORT
    @PostMapping("/buyAsset")
    fun buyAsset(user:User?, @RequestHeader("api-key") token:String?, @RequestBody reqBuyAssetDTO: AssetDTO.ReqBuyAssetDTO) : Response<Order> {
        val _user = userService.validateUserForAPI(user, token)
        //발행 출금된 NFT인지 확인.
        ethereumProvider.availableAsset(reqBuyAssetDTO.assetId)
        return Response(assetTradeService.buyAsset(_user, reqBuyAssetDTO))
    }

    //**API SUPPORT
    @PostMapping("/cancelBuyAsset")
    fun cancelBuyAsset(user:User?, @RequestHeader("api-key") token:String?, @RequestBody reqCancelBuyAssetDTO: AssetDTO.ReqCancelBuyAssetDTO) : Response<Order> {
        val _user = userService.validateUserForAPI(user, token)
        return Response(assetTradeService.cancelBuyAsset(_user, reqCancelBuyAssetDTO))
    }

    //**API SUPPORT
    @PostMapping("/modifyBuyAsset")
    fun modifyBuyAsset(user:User?, @RequestHeader("api-key") token:String?, @RequestBody reqModifyBuyAssetDTO: AssetDTO.ReqModifyBuyAssetDTO) : Response<Order> {
        val _user = userService.validateUserForAPI(user, token)
        return Response(assetTradeService.modifyBuyAsset(_user, reqModifyBuyAssetDTO))
    }
}