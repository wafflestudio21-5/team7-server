package com.example.cafe.article.service

import com.example.cafe.board.service.Board
import com.example.cafe.user.service.User
import java.time.LocalDateTime

data class ArticleBrief (
    val articleId: Long,
    val title: String,
    val author: User,
    val board: Board,
    val likeCount: Long,
    val viewCount: Long,
    val commentCount: Int,
    val createdAt: LocalDateTime,
    val isNotification: Boolean,
)