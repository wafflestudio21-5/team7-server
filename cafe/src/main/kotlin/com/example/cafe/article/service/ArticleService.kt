package com.example.cafe.article.service

import java.time.LocalDateTime

interface ArticleService {
    fun post(
        userId: String,
        title: String,
        content: String,
        createdAt: LocalDateTime = LocalDateTime.now(),
        boardId: Long,
        allowComments: Boolean,
        isNotification: Boolean,
    )

    fun get(id: Long): Article
}