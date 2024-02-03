package com.example.cafe.board.service

import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.article.service.ArticleBrief
import com.example.cafe.board.repository.BoardGroupRepository
import com.example.cafe.board.repository.BoardRepository
import com.example.cafe.user.repository.UserEntity
import com.example.cafe.user.service.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardServiceImpl (
    private val boardRepository: BoardRepository,
    private val boardGroupRepository: BoardGroupRepository,
    private val articleRepository: ArticleRepository,
) : BoardService {
    override fun get(): List<Board> {
        val boardList = boardRepository.findAll()

        return boardList.map {
            Board(
                id = it.id,
                name = it.name
            )
        }
    }

    override fun getGroup(): List<BoardGroup> {
        val boardGroupList = boardGroupRepository.findAllWithFetchJoin()
        val hotList = getHotBoardId()

        return boardGroupList.map {boardGroup->
            BoardGroup(
                id = boardGroup.id,
                name = boardGroup.name,
                boards = boardGroup.boards.map {board->
                    BoardSideBar(id = board.id, name = board.name, isHot = hotList.contains(board.id))
                }
            )
        }
    }

    @Transactional
    override fun getArticles(boardId: Long, page: Int, size: Int, sort: List<String>): Page<ArticleBrief> {
        val board = boardRepository.findById(boardId).orElseThrow{ BoardNotFoundException() }
        val pageable = PageRequest.of(page, size, sortBy(sort))
        val articleList = articleRepository.findByBoardId(boardId, pageable)

        articleList.forEach { articleEntity ->
            val commentCount = articleEntity.comments.size.toLong()
            articleEntity.commentCnt = commentCount
        }

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

    private fun sortBy(sort: List<String>): Sort {
        if (sort.size != 2) {
            throw IllegalSortArgumentException()
        }

        if (!isPropertyValid(sort[0]) || !isDirectionValid(sort[1])) {
            throw IllegalSortArgumentException()
        }

        val property = sort[0]
        return when (sort[1] == "desc") {
            true -> Sort.by(Sort.Direction.DESC, property, "id")
            false -> Sort.by(
                Sort.Order.asc(property),
                Sort.Order.desc("id")
            )
        }
    }

    private fun isPropertyValid(property: String): Boolean {
        val validProperties = listOf("createdAt", "title", "likeCnt", "viewCnt", "commentCnt")

        return validProperties.contains(property)
    }

    private fun isDirectionValid(direction: String): Boolean {
        val validDirection = listOf("desc", "asc")

        return validDirection.contains(direction)
    }

    private fun getHotBoardId(): List<Long> {
        return boardRepository.findTop3ByOrderByLikeCntDesc()
            .filter { it.likeCnt >= 10 }
            .map { it.id }
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
