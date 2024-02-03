package com.example.cafe.article.service

import com.example.cafe.article.controller.HotTimeType
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
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime
import java.util.Dictionary

@Service
class ArticleServiceImpl(
        private val articleRepository: ArticleRepository,
        private val boardRepository: BoardRepository,
        private val userRepository: UserRepository,
        private val articleLikeRepository: ArticleLikeRepository,
        private val articleViewRepository: ArticleViewRepository
) : ArticleService {

    @Transactional
    override fun post(
        userId: Long,
        title: String,
        content: String,
        createdAt: LocalDateTime,
        boardId: Long,
        allowComments: Boolean,
        isNotification: Boolean,
    ) : ArticleEntity {
        val user = userRepository.findById(userId).orElseThrow{ UserNotFoundException() }
        //board not selected -> id = 0
        val board = boardRepository.findById(boardId).orElseThrow{ BoardNotFoundException() }

        if(content=="") throw PostBadContentException()

        if(title=="") throw PostBadTitleException()

        userRepository.incrementArticleCount(userId)
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
        val article = articleRepository.findById(articleId).orElseThrow { ArticleNotFoundException() }

        val board = boardRepository.findById(boardId).orElseThrow{ BoardNotFoundException() }

        if(content=="") throw PostBadContentException()

        if(title=="") throw PostBadTitleException()

        if(userId != article.user.id) throw UnauthorizedModifyException()

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

    @Transactional
    override fun delete(
            articleId: Long,
            userId: Long
    ) {
        val article = articleRepository.findById(articleId).orElseThrow { ArticleNotFoundException() }

        if(article.user.id != userId) throw UnauthorizedModifyException()
        userRepository.decrementArticleCount(userId)
        articleRepository.delete(article)
    }

    @Transactional
    override fun get(id: Long): Article {
        val article = articleRepository.findById(id).orElseThrow{ ArticleNotFoundException() }
        val author = article.user
        val board = article.board
        val likeCount = article.likeCnt
        val viewCount = article.viewCnt
        val commentCount = article.comments.size.toLong()
        article.commentCnt = commentCount

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

    override fun getAroundArticleId(article: Article): Pair<Long?, Long?> {
        val articles = articleRepository.findByBoardIdInList(article.board.id).sortedBy { article ->
            article.createdAt
        }.map{it.id}
        val idx = articles.indexOf(article.id)
        val nextId = if(idx!=0){ articles[idx-1] } else{ null }
        val prevId = if(idx!=articles.size-1){ articles[idx+1] } else{ null }
        return Pair(prevId,nextId)
    }

    override fun getHotArticles(sortBy: String, pageable: Pageable, hotTimeType: HotTimeType): Page<ArticleBrief> {
        val articles = articleRepository.findTop200ByProperty(sortBy, pageable, hotTimeType)
        return convertPageToArticleBriefPage(articles)
    }

    override fun getArticles(
        pageable: Pageable
    ): Page<ArticleBrief> {
        val articles = articleRepository.findAll(pageable)
        return convertPageToArticleBriefPage(articles)
    }

    override fun getNotification(): List<ArticleBrief> {
        return articleRepository.findByIsNotificationTrue().map {article ->
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

    override fun deleteNotification(articleId: Long, user: User) {
        if(user.rank != User.Rank.ADMIN.name){
            throw UnauthorizedModifyException()
        } else{
            val article = articleRepository.findById(articleId).orElseThrow{ throw ArticleNotFoundException() }
            articleRepository.save(
                ArticleEntity(
                    id = article.id,
                    title = article.title,
                    content = article.content,
                    createdAt = article.createdAt,
                    viewCnt = article.viewCnt,
                    likeCnt = article.likeCnt,
                    user = article.user,
                    board = article.board,
                    allowComments = article.allowComments,
                    isNotification = false
                )
            )
        }
    }

    fun User(entity: UserEntity) = User(
        id = entity.id,
        nickname = entity.nickname,
        registerDate = entity.registerDate,
        email = entity.email,
        rank = entity.rank,
        visitCount = entity.visitCount,
        articlesCount = entity.articlesCount,
        commentsCount = entity.commentsCount
    )
    fun convertPageToArticleBriefPage(page: Page<ArticleEntity>): Page<ArticleBrief> {
        return page.map { article ->
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
}


