package com.example.cafe.article.service

import java.time.LocalDateTime

data class CommentedArticle(
    val id: Long,
    val title: String,
    val createdAt: LocalDateTime,
    val viewCnt: Long,
    val commentCnt: Long,
    val authorNickname: String,
)
