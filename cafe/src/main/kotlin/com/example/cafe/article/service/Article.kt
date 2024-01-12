package com.example.cafe.article.service

import com.example.cafe.user.service.User

data class Article(
    val articleId: Long,
    val title: String,
    val content: String,
    val author: User,
    val likeCount: Long,
    val viewCount: Long,
    val createdAt: String,
    val updatedAt: String,
    val isLiked: Boolean,
)