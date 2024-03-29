package com.example.cafe.user.service

import com.example.cafe.article.service.CommentedArticle
import com.example.cafe.article.service.UserArticleBrief
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate

interface UserService {
    fun signUp(username: String, password: String, name: String, email: String?, birthDate: String, phoneNumber: String, at: LocalDate = LocalDate.now()): User
    fun signIn(username: String, password: String): User
    fun updateProfile(id: Long, nickname: String, introduction: String, image: String): User
    fun delete(id: Long)
    fun getUserBrief(id: Long): UserBrief
    fun authenticate(accessToken: String): User
    fun getProfile(id: Long): UserProfile
    fun getUserInfo(nickname: String): UserInfo
    fun getLikeArticles(id: Long, pageable: Pageable): Page<UserArticleBrief>
    fun getUserArticles(username: String, pageable: Pageable): Page<UserArticleBrief>
    fun getUserComments(userId: Long, pageable: Pageable): Page<UserComment>
    fun getUserCommentedArticles(nickname: String, pageable: Pageable): Page<CommentedArticle>
}
