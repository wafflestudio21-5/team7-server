package com.example.cafe.board.controller

import com.example.cafe.article.controller.ArticlePostRequest
import com.example.cafe.article.service.ArticleBrief
import com.example.cafe.board.service.Board
import com.example.cafe.board.service.BoardGroup
import com.example.cafe.board.service.BoardLikeService
import com.example.cafe.board.service.BoardService
import org.springframework.web.bind.annotation.*

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
        @RequestParam userId: String
    ) {
        boardLikeService.create(userId, boardId)
    }

    @DeleteMapping("/api/v1/boards/{boardId}/likes")
    fun undoLikeBoard(
        @PathVariable boardId: Long,
        @RequestParam userId: String
    ) {
        boardLikeService.delete(userId, boardId)
    }

    @GetMapping("/api/v1/boards/likes")
    fun getLikeBoard(
        @RequestParam userId: String
    ):BoardResponse {
        return BoardResponse(boardLikeService.get(userId))
    }

    @GetMapping("/api/v1/boards/{boardId}/articles")
    fun getBoardArticle(
        @PathVariable boardId: Long,
    ):ArticleBriefResponse {
        return ArticleBriefResponse(boardService.getArticles(boardId))
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
