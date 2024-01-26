package com.example.cafe.board.service

import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.article.service.ArticleBrief
import com.example.cafe.board.repository.BoardGroupRepository
import com.example.cafe.board.repository.BoardRepository
import com.example.cafe.user.repository.UserEntity
import com.example.cafe.user.service.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl (
    private val boardRepository: BoardRepository,
    private val boardGroupRepository: BoardGroupRepository,
    private val articleRepository: ArticleRepository,
) : BoardService {
    override fun get(): List<Board> {
        val boardList = boardRepository.find()

        return boardList.map {
            Board(
                id = it.id,
                name = it.name
            )
        }
    }

    override fun getGroup(): List<BoardGroup> {
        val boardGroupList = boardGroupRepository.find()

        return boardGroupList.map {boardGroup->
            BoardGroup(
                id = boardGroup.id,
                name = boardGroup.name,
                boards = boardGroup.boards.map {board->
                    Board(id = board.id, name = board.name)
                }
            )
        }
    }

    override fun getArticles(boardId: Long, pageable: Pageable): Page<ArticleBrief> {
        val board = boardRepository.findById(boardId).orElseThrow{ BoardNotFoundException() }
        val articleList = articleRepository.findByBoardId(boardId, pageable)

        return articleList.map {article->
            ArticleBrief(
                id = article.id,
                title = article.title,
                createdAt = article.createdAt,
                viewCount = article.viewCnt,
                likeCount = article.likeCnt,
                commentCount = article.commentCnt,
                author = User(article.user),
                board = Board(id = board.id, name = board.name),
                isNotification = article.isNotification,
            )
        }
    }

    override fun getNotification(boardId: Long): List<ArticleBrief> {
        val board = boardRepository.findById(boardId).orElseThrow{ BoardNotFoundException() }
        val articleList = articleRepository.findByBoardIdAndNotification(boardId)

        return articleList.map {article->
            ArticleBrief(
                id = article.id,
                title = article.title,
                createdAt = article.createdAt,
                viewCount = article.viewCnt,
                likeCount = article.likeCnt,
                commentCount = article.commentCnt,
                author = User(article.user),
                board = Board(id = board.id, name = board.name),
                isNotification = article.isNotification,
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
}
