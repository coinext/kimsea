package io.tommy.kimsea.web.controllers.rest

import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.entity.domain.Wallet
import io.tommy.kimsea.web.services.UserService
import io.tommy.kimsea.web.services.WalletService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/wallets")
class WalletsRestController(
    private val userService: UserService,
    private val walletService: WalletService
) {

    @GetMapping("/getWalletByUserId")
    fun getWalletByUserId(@RequestParam userId:Long, user: User?): Response<Wallet> {
        return Response(walletService.getWalletByUserId(userId))
    }

    //**API SUPPORT
    @GetMapping("/getMyWallet")
    fun getMyWallet(user:User?, @RequestHeader("api-key") token:String?): Response<Wallet> {
        val _user = userService.validateUserForAPI(user, token)
        return Response(walletService.getWalletByUserId(_user.id!!))
    }
}