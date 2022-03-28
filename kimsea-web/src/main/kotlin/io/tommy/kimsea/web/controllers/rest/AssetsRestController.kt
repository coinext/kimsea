package io.tommy.kimsea.web.controllers.rest

import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.Asset
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.enums.CategoryEnum
import io.tommy.kimsea.web.services.asset.AssetService
import io.tommy.kimsea.web.utils.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/assets")
class AssetsRestController(
    val assetService: AssetService
) {
    val log = logger()


    @GetMapping("/getPopularAssets")
    fun getPopularAssets(user:User?, @RequestParam query:String?): Response<List<Asset>> {
        return Response(assetService.getPopularAssets(user, query, 0, 20))
    }

    @GetMapping("/getRecentlyAssets")
    fun getRecentlyAssets(user:User?, @RequestParam query:String?): Response<List<Asset>> {
        return Response(assetService.getRecentlyAssets(user, query, 0, 20))
    }

    @GetMapping("/getAssets")
    fun getAssets(user:User?, @RequestParam query:String?): Response<List<Asset>> {
        return Response(assetService.getAssets(user, query, 0, 20))
    }

    @GetMapping("/getAssetById")
    fun getAssetById(user:User?, @RequestParam assetId:Long): Response<Asset> {
        return Response(assetService.getAssetById(user, assetId))
    }

    @GetMapping("/getAssetsByCategory")
    fun getAssetsByCategory(user:User?, @RequestParam category:CategoryEnum, @RequestParam query:String?, @RequestParam pageNo:Int, @RequestParam pageSize:Int): Response<List<Asset>> {
        return Response(assetService.getAssetsByCategory(user, category, query, pageNo, pageSize))
    }

    @GetMapping("/getAssetsByCreator")
    fun getAssetsByCreator(user:User?, @RequestParam userId:Long, @RequestParam query:String?, @RequestParam pageNo:Int, @RequestParam pageSize:Int): Response<List<Asset>> {
        return Response(assetService.getAssetsByCreator(user, userId, query, pageNo, pageSize))
    }

    @GetMapping("/getAssetsByOwner")
    fun getAssetsByOwner(user:User?, @RequestParam userId:Long, @RequestParam query:String?, @RequestParam pageNo:Int, @RequestParam pageSize:Int): Response<List<Asset>> {
        return Response(assetService.getAssetsByOwner(user, userId, query, pageNo, pageSize))
    }
}