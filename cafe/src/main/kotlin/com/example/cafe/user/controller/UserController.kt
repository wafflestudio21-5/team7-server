package com.example.cafe.user.controller

import com.example.cafe.article.service.CommentedArticle
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
            email = request.email.orEmpty(),
            birthDate = request.birthDate,
            phoneNumber = request.phoneNumber,
        )
    }

    @PutMapping("/api/v1/users/user-profile")
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

    @GetMapping("/api/v1/users/user-profile")
    fun getProfile(
        @Authenticated user: User
    ): ProfileResponse {
        val user = userService.getProfile(user.id)
        return ProfileResponse(nickname = user.nickname, content = user.introduction, image = user.image)
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

    @GetMapping("/api/v1/users/user/{nickname}")
    fun getUserInfo(
        @PathVariable nickname: String
    ): UserInfoResponse {
        return UserInfoResponse(userInfo = userService.getUserInfo(nickname))
    }

    @GetMapping("/api/v1/users/liked-articles")
    fun getLikedArticles(
        @Authenticated user: User,
        @RequestParam("page", defaultValue = "1") page: Int,
    ): UserArticleBriefPageResponse {
        return UserArticleBriefPageResponse(
            userService.getLikeArticles(
                user.id,
                PageRequest.of(page-1, 15, Sort.by(Sort.Direction.DESC, "id"))
            )
        )
    }

    @GetMapping("/api/v1/users/articles/{nickname}")
    fun getUserArticles(
        @PathVariable nickname: String,
        @RequestParam("page", defaultValue = "1") page: Int,
    ): UserArticleBriefPageResponse {
        return UserArticleBriefPageResponse(
            userService.getUserArticles(
                nickname,
                PageRequest.of(page-1, 15, Sort.by(Sort.Direction.DESC, "createdAt", "id"))
            )
        )
    }

    @GetMapping("/api/v1/users/comments")
    fun getUserComments(
        @RequestParam("page", defaultValue = "1") page: Int,
        @Authenticated user: User,
    ): UserCommentsPageResponse {
        return UserCommentsPageResponse(
            userService.getUserComments(
                user.id,
                PageRequest.of(page - 1, 15, Sort.by(Sort.Direction.DESC, "lastModified", "id"))
            )
        )
    }

    @GetMapping("/api/v1/users/{nickname}/commented-articles")
    fun getUserCommentedArticles(
        @PathVariable nickname: String,
        @RequestParam("page", defaultValue = "1") page: Int,
    ): CommentedArticlesPageResponse {
        return CommentedArticlesPageResponse(
            userService.getUserCommentedArticles(
                nickname,
                PageRequest.of(page-1, 15, Sort.by(Sort.Direction.DESC, "id", "createdAt"))
            )
        )
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

    data class SignUpRequest(
        val username: String,
        val name: String,
        val password: String,
        val email: String?,
        val birthDate: String,
        val phoneNumber: String,
    )

    data class UpdateProfileRequest(
        val nickname: String,
        val content: String,
        val image: String
    )

    data class UserBriefResponse(
        val userBrief: UserBrief
    )

    data class UserInfoResponse(
        val userInfo: UserInfo
    )

    data class ProfileResponse(
        val nickname: String,
        val content: String?,
        val image: String?
    )

    data class UserArticleBriefPageResponse(
        val articleBrief: Page<UserArticleBrief>
    )

    data class  UserCommentsPageResponse(
        val userComments: Page<UserComment>
    )

    data class CommentedArticlesPageResponse(
        val commentedArticles: Page<CommentedArticle>
    )
}

