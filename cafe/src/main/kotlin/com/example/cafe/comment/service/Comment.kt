package com.example.cafe.comment.service

import java.time.LocalDateTime

data class Comment(
    val id: Long,
    val content: String,
    var lastModified: LocalDateTime,
    val nickname: String,
    val recomments: List<Recomment> = emptyList(),
)
