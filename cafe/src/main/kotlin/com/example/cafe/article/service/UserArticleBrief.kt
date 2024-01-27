package com.example.cafe.article.service

import com.example.cafe.user.service.User
import java.time.LocalDateTime

data class UserArticleBrief (
    val id: Long,
    val title: String,
    val createdAt: LocalDateTime,
    val viewCount: Long,
    val likeCount: Long,
    val commentCount: Long,
    val author: User,
)