package com.example.cafe.user.controller

import com.example.cafe.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/api/v1/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ): SignUpResponse {
        val user = userService.signUp(
            userId = request.userId,
            username = request.username,
            password = request.password,
            email = request.email,
            birthDate = request.birthDate,
            phoneNumber = request.phoneNumber,
        )
        return SignUpResponse(userId = user.userId, username = user.username)
    }
}

data class SignUpRequest(
    val userId: String,
    val username: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val phoneNumber: String,
)

data class SignUpResponse(
    val userId: String,
    val username: String,
)