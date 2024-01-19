package com.example.cafe.user.controller

import com.example.cafe.user.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.contracts.contract

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
        return SignUpResponse(userId = user.userId)
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): SignInResponse {
        val user = userService.signIn(
            userId = request.userId,
            password = request.password
        )

        return SignInResponse(user.userId)
    }

    @PostMapping("/api/v1/signout")
    fun signOut(
        @RequestBody request: SignOutRequest,
    ) {
        userService.signOut(
            userId = request.userId
        )
    }

    @PutMapping("/api/v1/users/user")
    fun updateProfile(
        @RequestBody request: UpdateProfileRequest,
    ): UpdateProfileResponse {
        val user = userService.updateProfile(
            userId = request.userId,
            nickname = request.nickname,
            introduction = request.content
        )

        return UpdateProfileResponse(userId = user.userId)
    }

    @DeleteMapping("/api/v1/users/user")
    fun deleteUser(
        @RequestBody request: UserDeleteRequest,
    ) {
        userService.delete(userId = request.userId)
    }

    @ExceptionHandler
    fun handleException(e: UserException): ResponseEntity<Unit> {
        val status = when (e) {
            is SignUpBadUserIdException, is SignUpBadPasswordException, is SignUpBadEmailException, is SignUpBadBirthDateException, is SignUpBadPhoneNumberException -> 400
            is SignUpUserIdConflictException, is NicknameConflictException -> 409
            is SignInUserNotFoundException, is SignInInvalidPasswordException, is SignOutUserNotFoundException, is UserNotFoundException -> 404
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

data class SignInRequest(
    val userId: String,
    val password: String,
)

data class SignOutRequest(
    val userId: String
)

data class UpdateProfileRequest(
    val userId: String,
    val nickname: String,
    val content: String
)

data class UserDeleteRequest(
    val userId: String
)

data class SignUpResponse(
    val userId: String
)

data class SignInResponse(
    val userId: String
)

data class UpdateProfileResponse(
    val userId: String
)
