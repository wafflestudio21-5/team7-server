package com.example.cafe.comment.service

import java.time.LocalDateTime

data class Comment(
    val id: Long,
    val content: String,
    var updatedAt: LocalDateTime,
    val user: String,
)
