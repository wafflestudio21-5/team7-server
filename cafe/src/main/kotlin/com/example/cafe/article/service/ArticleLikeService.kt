package com.example.cafe.article.service

interface ArticleLikeService {
    fun exists(articleId: Long, userId: String): Boolean
    fun create(articleId: Long, userId: String)
    fun delete(articleId: Long, userId: String)
}