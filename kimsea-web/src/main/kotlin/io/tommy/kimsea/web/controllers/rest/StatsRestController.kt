package io.tommy.kimsea.web.controllers.rest

import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.dto.StatDTO
import io.tommy.kimsea.web.entity.domain.Asset
import io.tommy.kimsea.web.services.StatsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/stats")
class StatsRestController(
    private val statsService: StatsService
) {


    @GetMapping("/getStatsUsers")
    fun getStatsUsers(@RequestParam pageNo:Int, @RequestParam pageSize:Int) : Response<List<StatDTO.UserDTO>> {
        return Response(statsService.getStatsUsers(pageNo, pageSize))
    }

    @GetMapping("/getStatsCreators")
    fun getStatsCreators(@RequestParam pageNo:Int, @RequestParam pageSize:Int) : Response<List<StatDTO.CreatorDTO>> {
        return Response(statsService.getStatsCreators(pageNo, pageSize))
    }

    @GetMapping("/getStatsAssets")
    fun getStatsAssets(@RequestParam pageNo:Int, @RequestParam pageSize:Int) : Response<List<Asset>> {
        return Response(statsService.getStatsAssets(pageNo, pageSize))
    }
}