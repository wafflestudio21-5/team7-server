package com.example.cafe.article.service

import com.example.cafe.article.repository.ArticleEntity
import com.example.cafe.user.service.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

interface ArticleService {
    fun post(
        userId: Long,
        title: String,
        content: String,
        createdAt: LocalDateTime = LocalDateTime.now(),
        boardId: Long,
        allowComments: Boolean,
        isNotification: Boolean,
    ) : ArticleEntity
    fun modify(
            userId: Long,
            articleId: Long,
            title: String,
            content: String,
            boardId: Long,
            allowComments: Boolean,
            isNotification: Boolean
    ): ArticleEntity

    fun delete(
            articleId: Long,
            userId: Long
    )
    fun get(id: Long): Article

    fun getHotArticles(pageable: Pageable): Page<ArticleBrief>

    fun getArticles(userId: Long?): List<ArticleBrief>

    enum class HotSortType {
        VIEW, LIKE, COMMENT
    }
}