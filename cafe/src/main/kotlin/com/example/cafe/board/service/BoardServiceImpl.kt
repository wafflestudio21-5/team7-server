package com.example.cafe.board.service

import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.article.service.Article
import com.example.cafe.article.service.ArticleBrief
import com.example.cafe.board.repository.BoardGroupRepository
import com.example.cafe.board.repository.BoardRepository
import com.example.cafe.user.service.User
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

    override fun getArticles(boardId: Long): List<ArticleBrief> {
        val articleList = articleRepository.findByBoardId(boardId)

        return articleList.map {article->
            ArticleBrief(
                articleId = article.id,
                title = article.title,
                author = User(userId = article.user.userId, username = article.user.username),
                board = Board(id = article.board.id, name = article.board.name),
                likeCount = article.likeCnt,
                viewCount = article.viewCnt,
                commentCount = article.comments.size,
                createdAt = article.createdAt,
                isNotification = article.isNotification,
            )
        }
    }

}
