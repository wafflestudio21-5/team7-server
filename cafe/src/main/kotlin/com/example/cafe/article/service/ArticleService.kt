package com.example.cafe.article.service

import com.example.cafe.article.controller.HotTimeType
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

    fun getHotArticles(sortBy:String, pageable: Pageable, hotTimeType: HotTimeType): Page<ArticleBrief>

    fun getArticles(pageable: Pageable): Page<ArticleBrief>

    fun getNotification(): List<ArticleBrief>
    fun getAroundArticleId(article: Article): Pair<Long?,Long?>

    enum class HotSortType {
        VIEW, LIKE, COMMENT
    }
}