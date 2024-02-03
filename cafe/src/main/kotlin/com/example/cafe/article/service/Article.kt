package com.example.cafe.article.service

import com.example.cafe.board.service.Board
import com.example.cafe.user.service.User

data class Article(
        val id: Long,
        val title: String,
        val content: String,
        val createdAt: String,
        val viewCount: Long,
        val likeCount: Long,
        val commentCount: Long,
        val author: User,
        val board: Board,
        val allowComments: Boolean,
        val isNotification: Boolean
)