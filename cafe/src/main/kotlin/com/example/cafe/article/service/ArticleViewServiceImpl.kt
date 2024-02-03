package com.example.cafe.article.service

import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.article.repository.ArticleViewEntity
import com.example.cafe.article.repository.ArticleViewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.time.LocalDateTime
import java.util.concurrent.Executors
import java.util.concurrent.Future

@Service
class ArticleViewServiceImpl(
        private val articleRepository: ArticleRepository,
        private val articleViewRepository: ArticleViewRepository,
        txManager: PlatformTransactionManager,
) : ArticleViewService {
    private val executors = Executors.newFixedThreadPool(8)
    private val txTemplate = TransactionTemplate(txManager)

    override fun create(articleId: Long, userId: Long, createdAt: LocalDateTime): Future<Boolean> {
        return executors.submit<Boolean> {
            txTemplate.execute {
                if (shouldIgnore(articleId = articleId, userId = userId, at = createdAt)) {
                    return@execute false
                }

                articleViewRepository.save(
                        ArticleViewEntity(
                                articleId = articleId,
                                userId = userId,
                                createdAt = createdAt
                        )
                )

                articleRepository.incrementViewCnt(articleId)

                true
            }
        }
    }
    private fun shouldIgnore(articleId: Long, userId: Long, at: LocalDateTime): Boolean =
            articleViewRepository.existsByArticleIdAndUserIdAndCreatedAtAfterAndCreatedAtBefore(
                    articleId = articleId,
                    userId = userId,
                    after = at.minusMinutes(1),
                    before = at
            )
}