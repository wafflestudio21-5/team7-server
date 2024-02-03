package com.example.cafe.article.service

interface ArticleLikeService {
    fun exists(articleId: Long, userId: Long): Boolean
    fun create(articleId: Long, userId: Long)
    fun delete(articleId: Long, userId: Long)
}