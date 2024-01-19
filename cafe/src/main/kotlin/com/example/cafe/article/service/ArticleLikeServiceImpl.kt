package com.example.cafe.article.service

import com.example.cafe.article.repository.ArticleLikeEntity
import com.example.cafe.article.repository.ArticleLikeRepository
import com.example.cafe.article.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleLikeServiceImpl(
        private val articleRepository: ArticleRepository,
        private val articleLikeRepository: ArticleLikeRepository
) : ArticleLikeService {
    override fun exists(articleId: Long, userId: String): Boolean {
        return articleLikeRepository.findByArticleIdAndUserId(articleId= articleId, userId=userId) != null
    }

    override fun create(articleId: Long, userId: String) {
        if (articleRepository.findById(articleId).isEmpty) {
            throw ArticleNotFoundException()
        }

        if (exists(articleId = articleId, userId = userId)) {
            throw ArticleAlreadyLikedException()
        }

        articleLikeRepository.save(
                ArticleLikeEntity(
                        articleId = articleId,
                        userId = userId
                )
        )
    }

    override fun delete(articleId: Long, userId: String) {
        val like = articleLikeRepository.findByArticleIdAndUserId(articleId = articleId, userId = userId)
                ?: throw ArticleNotLikedException()
        articleLikeRepository.delete(like)
    }

}
