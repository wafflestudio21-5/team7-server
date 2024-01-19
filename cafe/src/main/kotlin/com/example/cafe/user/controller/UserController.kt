package com.example.cafe.user.controller

import com.example.cafe.user.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
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

    @ExceptionHandler
    fun handleException(e: UserException): ResponseEntity<Unit> {
        val status = when (e) {
            is SignUpBadUserIdException, is SignUpBadPasswordException, is SignUpBadEmailException, is SignUpBadBirthDateException, is SignUpBadPhoneNumberException -> 400
            is SignUpUserIdConflictException -> 409
            is SignInUserNotFoundException, is SignInInvalidPasswordException -> 404
        }

        return ResponseEntity.status(status).build()
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