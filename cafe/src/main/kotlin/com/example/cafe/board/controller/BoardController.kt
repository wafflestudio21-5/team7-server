package com.example.cafe.board.controller

import com.example.cafe.article.service.ArticleBrief
import com.example.cafe.board.service.*
import com.example.cafe.user.service.Authenticated
import com.example.cafe.user.service.User
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort

@RestController
class BoardController(
    private val boardService: BoardService,
    private val boardLikeService: BoardLikeService,
) {
    @GetMapping("/api/v1/boards-in-group")
    fun getBoardGroup(
    ):BoardGroupResponse {
        return BoardGroupResponse(boardService.getGroup())
    }

    @GetMapping("/api/v1/boards")
    fun getBoard(
    ):BoardResponse {
        return BoardResponse(boardService.get())
    }

    @PostMapping("/api/v1/boards/{boardId}/likes")
    fun likeBoard(
        @PathVariable boardId: Long,
        @Authenticated user: User
    ) {
        boardLikeService.create(user.id, boardId)
    }

    @DeleteMapping("/api/v1/boards/{boardId}/likes")
    fun undoLikeBoard(
        @PathVariable boardId: Long,
        @Authenticated user: User
    ) {
        boardLikeService.delete(user.id, boardId)
    }

    @GetMapping("/api/v1/boards/likes")
    fun getLikeBoard(
        @Authenticated user: User
    ):BoardResponse {
        return BoardResponse(boardLikeService.get(user.id))
    }

    @GetMapping("/api/v1/boards/{boardId}/articles")
    fun getBoardArticle(
        @PathVariable boardId: Long,
        @RequestParam("size", defaultValue = "15") size: Int,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("sort", defaultValue = "createdAt,desc") sort: List<String>,
    ):ArticleBriefPageResponse {
        var desc = true
        if (sort.size > 1) {
            if (sort[1] == "asc") desc = false
        }
        val property = sort[0]

        val sortBy = when (desc) {
            true -> Sort.by(Sort.Direction.DESC, property, "id")
            false -> Sort.by(
                Sort.Order.asc(property),
                Sort.Order.desc("id")
            )
        }

        return ArticleBriefPageResponse(boardService.getArticles(boardId, PageRequest.of(page, size, sortBy)))
    }

    @GetMapping("/api/v1/boards/{boardId}/notification")
    fun getBoardNotification(
        @PathVariable boardId: Long,
    ):ArticleBriefResponse {
        return ArticleBriefResponse(boardService.getNotification(boardId))
    }

    @ExceptionHandler
    fun handleException(e: BoardException): ResponseEntity<Unit> {
        val status = when (e) {
            is BoardUserNotFoundException, is BoardNotFoundException, is BoardNeverLikedException -> 404
            is BoardAlreadyLikedException -> 409
        }

        return ResponseEntity.status(status).build()
    }

}

data class BoardGroupResponse(
    val boardGroups: List<BoardGroup>,
)

data class BoardResponse(
    val boards: List<Board>,
)

data class ArticleBriefResponse(
    val articleBrief: List<ArticleBrief>
)

data class ArticleBriefPageResponse(
    val articleBrief: Page<ArticleBrief>
)
