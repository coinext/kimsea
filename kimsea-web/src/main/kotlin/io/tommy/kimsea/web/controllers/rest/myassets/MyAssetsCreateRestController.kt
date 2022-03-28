package io.tommy.kimsea.web.controllers.rest.myassets

import io.tommy.kimsea.web.dto.AssetDTO
import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.Asset
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.services.UserService
import io.tommy.kimsea.web.services.asset.AssetCreateService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/myassets")
class MyAssetsCreateRestController(
    private val userService: UserService,
    private val assetCreateService: AssetCreateService,
) {

    //**API SUPPORT
    @PostMapping("/createAsset", produces = ["application/json"],consumes = ["multipart/form-data"])
    suspend fun createAsset(user:User?, @RequestHeader("api-key") token:String?, reqCreateAssetDTO: AssetDTO.ReqCreateAssetDTO) : Response<Asset> {
        val _user = userService.validateUserForAPI(user, token)
        return Response(assetCreateService.createAsset(_user, reqCreateAssetDTO))
    }
}