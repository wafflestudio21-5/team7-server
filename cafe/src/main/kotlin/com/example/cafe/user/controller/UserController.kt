package com.example.cafe.user.controller

import com.example.cafe.user.service.*
import com.example.cafe.article.service.UserArticleBrief
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Page

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/api/v1/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ) {
        userService.signUp(
            username = request.username,
            name = request.name,
            password = request.password,
            email = request.email,
            birthDate = request.birthDate,
            phoneNumber = request.phoneNumber,
        )
    }

    @PutMapping("/api/v1/users/user")
    fun updateProfile(
        @RequestBody request: UpdateProfileRequest,
        @Authenticated user: User
    ) {
        userService.updateProfile(
            id = user.id,
            nickname = request.nickname,
            introduction = request.content,
            image = request.image
        )
    }

    @DeleteMapping("/api/v1/users/user")
    fun deleteUser(
        @Authenticated user: User
    ) {
        userService.delete(id = user.id)
    }

    @GetMapping("/api/v1/users/user-brief")
    fun getUserBrief(
        @Authenticated user: User
    ): UserBriefResponse {
        return UserBriefResponse(userBrief = userService.getUserBrief(user.id))
    }

    @GetMapping("/api/v1/users/liked-articles")
    fun getLikedArticles(
        @Authenticated user: User,
        @RequestParam("size", defaultValue = "15") size: Int,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("sort", defaultValue = "id") sort: String,
    ): UserArticleBriefPageResponse {
        return UserArticleBriefPageResponse(userService.getLikeArticles(user.id, PageRequest.of(page, size, Sort.by(sort))))
    }

    @GetMapping("/api/v1/users/articles")
    fun getMyArticles(
        @Authenticated user: User,
        @RequestParam("size", defaultValue = "15") size: Int,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("sort", defaultValue = "createdAt") sort: String,
    ): UserArticleBriefPageResponse {
        return UserArticleBriefPageResponse(userService.getMyArticles(user.id, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort))))
    }

    @ExceptionHandler
    fun handleException(e: UserException): ResponseEntity<Unit> {
        val status = when (e) {
            is SignUpBadUserIdException, is SignUpBadPasswordException, is SignUpBadEmailException, is SignUpBadBirthDateException, is SignUpBadPhoneNumberException -> 400
            is InvalidTokenException, is ExpiredTokenException -> 401
            is SignUpUserIdConflictException, is NicknameConflictException -> 409
            is SignInUserNotFoundException, is SignInInvalidPasswordException, is SignOutUserNotFoundException, is UserNotFoundException -> 404
        }

        return ResponseEntity.status(status).build()
    }
}

data class SignUpRequest(
    val username: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val phoneNumber: String,
)

data class SignInRequest(
    val username: String,
    val password: String,
)

data class UpdateProfileRequest(
    val nickname: String,
    val content: String,
    val image: String
)

data class UserBriefResponse(
    val userBrief: UserBrief
)
data class UserArticleBriefPageResponse(
    val articleBrief: Page<UserArticleBrief>
)
