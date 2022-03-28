package io.tommy.kimsea.web.controllers.rest.myassets

import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.Asset
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.enums.AssetStatusEnum
import io.tommy.kimsea.web.services.UserService
import io.tommy.kimsea.web.services.asset.AssetService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/myassets")
class MyAssetsRestController(
    private val userService: UserService,
    private val assetService: AssetService,
) {

    //**API SUPPORT
    @GetMapping("/getMyAssetsByStatus")
    fun getMyAssetsByStatus(user:User?, @RequestHeader("api-key") token:String?, @RequestParam status: AssetStatusEnum, @RequestParam query:String?, @RequestParam(defaultValue = "0") pageNo:Int, @RequestParam(defaultValue = "20") pageSize:Int): Response<List<Asset>> {
        val _user = userService.validateUserForAPI(user, token)
        return Response(assetService.getMyAssetsByStatus(_user, status, query, pageNo, pageSize))
    }
}