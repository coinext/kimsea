package io.tommy.kimsea.web.controllers.rest

import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.AssetHistory
import io.tommy.kimsea.web.services.AssetHistoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/assetHistories")
class AssetHistoriesRestController(
    private val assetHistoryService: AssetHistoryService
) {

    @GetMapping("/getAssetHistoriesByAssetId")
    fun getAssetHistoriesByAssetId(@RequestParam assetId:Long, @RequestParam(required = false, defaultValue = "0") pageNo:Int, @RequestParam(required = false, defaultValue = "20") pageSize:Int): Response<List<AssetHistory>> {
        return Response(assetHistoryService.getOrderHistoriesByAssetId(assetId, pageNo, pageSize))
    }

}