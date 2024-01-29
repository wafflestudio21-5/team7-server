package com.example.cafe.user.service

import java.time.LocalDateTime

data class UserComment(
    val id: Long,
    val articleId: Long,
    val content: String,
    val nickname: String,
    val lastModified: LocalDateTime,
    val articleTitle: String,
    val articleCommentCnt: Long,
)
