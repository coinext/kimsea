package io.tommy.kimsea.web.controllers.rest.myapi

import io.tommy.kimsea.web.dto.MyApiInfoDTO
import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/myapi")
class MyApiRestController(
    val userService: UserService
) {

    @GetMapping("/getMyApiInfo")
    fun getMyApiInfo(user: User): Response<MyApiInfoDTO> {
        return Response(MyApiInfoDTO(user.apiKey!!, userService.getQuata(user)))
    }
}