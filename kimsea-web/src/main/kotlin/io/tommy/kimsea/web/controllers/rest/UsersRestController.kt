package io.tommy.kimsea.web.controllers.rest

import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.services.UserService
import io.tommy.kimsea.web.utils.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UsersRestController(
    val userService: UserService
) {
    val log = logger()

    @GetMapping("/getUserById")
    fun getUserById(user:User?, @RequestParam userId:Long): Response<User> {
        return Response(userService.getUserById(userId))
    }
}