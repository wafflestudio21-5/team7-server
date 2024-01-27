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
    override fun exists(articleId: Long, userId: Long): Boolean {
        return articleLikeRepository.findByArticleIdAndUserId(articleId= articleId, userId=userId) != null
    }

    override fun create(articleId: Long, userId: Long) {
        val article = articleRepository.findById(articleId).orElseThrow{ ArticleNotFoundException() }

        if (exists(articleId = articleId, userId = userId)) {
            throw ArticleAlreadyLikedException()
        }

        articleLikeRepository.save(
                ArticleLikeEntity(
                        article = article,
                        userId = userId
                )
        )
        articleRepository.incrementLikeCnt(articleId)
    }

    override fun delete(articleId: Long, userId: Long) {
        val like = articleLikeRepository.findByArticleIdAndUserId(articleId = articleId, userId = userId)
                ?: throw ArticleNotLikedException()
        articleLikeRepository.delete(like)
        articleRepository.decrementLikeCnt(articleId)
    }

}
