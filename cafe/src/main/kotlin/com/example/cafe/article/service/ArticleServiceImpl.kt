package com.example.cafe.article.service

import com.example.cafe.article.repository.ArticleEntity
import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.user.repository.UserRepository
import org.springframework.stereotype.Service
import com.example.cafe.article.repository.ArticleLikeRepository
import com.example.cafe.article.repository.ArticleViewRepository
import com.example.cafe.board.repository.BoardRepository
import com.example.cafe.board.service.Board
import com.example.cafe.user.repository.UserEntity
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
        userId: Long,
        title: String,
        content: String,
        createdAt: LocalDateTime,
        boardId: Long,
        allowComments: Boolean,
        isNotification: Boolean,
    ) : ArticleEntity {

        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException() }

        //board not selected -> id = 0
        val board = boardRepository.findById(boardId).get()

        if(content=="") throw PostBadContentException()

        if(title=="") throw PostBadTitleException()

        return articleRepository.save(
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
    override fun modify(
            userId: Long,
            articleId: Long,
            title: String,
            content: String,
            boardId: Long,
            allowComments: Boolean,
            isNotification: Boolean
    ): ArticleEntity {
        //if(user.userId != userId) throw UnauthorizedModifyException()

        val board = boardRepository.findById(boardId).orElseThrow{ BoardNotFoundException() }

        if(content=="") throw PostBadContentException()

        if(title=="") throw PostBadTitleException()

        val article = articleRepository.findById(articleId).orElseThrow { ArticleNotFoundException() }

        return articleRepository.save(
                ArticleEntity(
                        id = article.id,
                        title = title,
                        content = content,
                        createdAt = article.createdAt,
                        viewCnt = article.viewCnt,
                        likeCnt = article.likeCnt,
                        user = article.user,
                        board = board,
                        allowComments = allowComments,
                        isNotification = isNotification
                )
        )
    }

    override fun delete(
            articleId: Long,
            userId: Long
    ) {
        val article = articleRepository.findById(articleId).orElseThrow { ArticleNotFoundException() }

        if(article.user.id != userId) throw UnauthorizedModifyException()

        articleRepository.delete(article)
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
            author = User(author),
            board = Board(
                id = board.id,
                name = board.name,
            ),
            allowComments = article.allowComments,
            isNotification = article.isNotification,
            commentCount = article.commentCnt,
        )
    }

    override fun getHotArticles(sortBy: ArticleService.HotSortType): List<ArticleBrief> {
        val articles = when(sortBy) {
            ArticleService.HotSortType.VIEW -> articleRepository.findAllByOrderByViewCntDesc()
            ArticleService.HotSortType.LIKE -> articleRepository.findAllByOrderByLikeCntDesc()
            ArticleService.HotSortType.COMMENT -> articleRepository.findAllByOrderByCommentCntDesc()
        }
        return articles.map {article->
            ArticleBrief(
                id = article.id,
                title = article.title,
                createdAt = article.createdAt,
                viewCount = article.viewCnt,
                likeCount = article.likeCnt,
                commentCount = article.commentCnt,
                author = User(article.user),
                board = Board(id = article.board.id, name = article.board.name),
                isNotification = article.isNotification,
            )
        }
    }

    fun User(entity: UserEntity) = User(
        id = entity.id,
        nickname = entity.nickname,
        registerDate = entity.registerDate,
        email = entity.email,
        rank = entity.rank.name,
        visitCount = entity.visitCount,
        articlesCount = entity.articlesCount,
        commentsCount = entity.commentsCount
    )
}


