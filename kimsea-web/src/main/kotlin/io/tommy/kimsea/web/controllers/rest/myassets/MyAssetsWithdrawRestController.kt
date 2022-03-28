package io.tommy.kimsea.web.controllers.rest.myassets

import io.tommy.kimsea.web.dto.AssetDTO
import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.Transaction
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.providers.EthereumProvider
import io.tommy.kimsea.web.services.UserService
import io.tommy.kimsea.web.services.asset.AssetWithdrawService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/myassets")
class MyAssetsWithdrawRestController(
    private val userService: UserService,
    private val assetWithdrawService: AssetWithdrawService,
    private val ethereumProvider: EthereumProvider
) {

    //**API SUPPORT
    @PostMapping("/withdraw")
    fun withdraw(user:User?, @RequestHeader("api-key") token:String?,  @RequestBody reqWithdrawAssetDTO: AssetDTO.ReqWithdrawAssetDTO) : Response<Transaction> {
        val _user = userService.validateUserForAPI(user, token)
        ethereumProvider.availableAsset(reqWithdrawAssetDTO.assetId)
        return Response(assetWithdrawService.regWithdraw(_user, reqWithdrawAssetDTO))
    }
}