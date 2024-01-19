package com.example.cafe.article.service

import com.example.cafe.article.repository.ArticleEntity
import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.user.repository.UserRepository
import org.springframework.stereotype.Service
import com.example.cafe.article.repository.ArticleLikeRepository
import com.example.cafe.article.repository.ArticleViewRepository
import com.example.cafe.board.repository.BoardRepository
import com.example.cafe.board.service.Board
import com.example.cafe.user.service.User
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
    override fun get(id: Long): Article {
        val article = articleRepository.findById(id).get()
        val author = article.user
        val board = article.board
        val likeCount = article.likeCnt
        val viewCount = article.viewCnt
        return Article(
            id = id,
            title = article.title,
            content = article.content,
            createdAt = article.createdAt.toString(),
            viewCount = viewCount,
            likeCount = likeCount,
            author = User(
                userId = author.userId,
                username = author.username,
            ),
            board = Board(
                id = board.id,
                name = board.name,
            ),
            allowComments = article.allowComments,
            isNotification = article.isNotification,
        )
    }
}
