package com.example.cafe.article.repository

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ArticleViewRepository : JpaRepository<ArticleViewEntity, Long>{
    fun existsByArticleIdAndUserIdAndCreatedAtAfterAndCreatedAtBefore(
            articleId: Long,
            userId: Long,
            after: LocalDateTime,
            before: LocalDateTime,
    ): Boolean

}