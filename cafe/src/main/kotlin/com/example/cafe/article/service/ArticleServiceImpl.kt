package com.example.cafe.article.service

import com.example.cafe.article.repository.ArticleEntity
import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.user.repository.UserRepository
import org.springframework.stereotype.Service
import com.example.cafe.article.repository.ArticleLikeRepository
import com.example.cafe.article.repository.ArticleViewRepository
import com.example.cafe.board.repository.BoardRepository
import java.time.LocalDateTime

@Service
class ArticleServiceImpl(
        private val articleRepository: ArticleRepository,
        private val boardRepository: BoardRepository,
        private val userRepository: UserRepository,
        private val articleLikeRepository: ArticleLikeRepository,
        private val articleViewRepository: ArticleViewRepository
) : ArticleService {

    override fun post(
        userId: String,
        title: String,
        content: String,
        createdAt: LocalDateTime,
        boardId: Long,
        allowComments: Boolean,
        isNotification: Boolean,
    ) {

        val user = userRepository.findByUserId(userId)
            ?: throw UserNotFoundException()

        //board not selected -> id = 0
        val board = boardRepository.findById(boardId).get()


        if(content=="") throw PostBadContentException()

        if(title=="") throw PostBadTitleException()

        articleRepository.save(
            ArticleEntity(
                title = title,
                content = content,
                createdAt = createdAt,
                board = board,
                user = user,
                allowComments = allowComments,
                isNotification = isNotification
            )
        )
    }
}
