package com.example.cafe.article.controller

import com.example.cafe.article.service.*
import com.example.cafe.user.service.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ArticleController(
    private val articleService: ArticleService,
) {

    @PostMapping("/api/v1/article/post/{userId}")
    fun post(
            @RequestBody request: ArticlePostRequest,
        //need to be populated
            @PathVariable userId: String,
    ) {
        articleService.post(
            userId = userId,
            title = request.title,
            content = request.content,
            boardId = request.boardId,
            allowComments = request.allowComments,
            isNotification = request.isNotification,
        )
    }

    @ExceptionHandler
    fun handleException(e: ArticleException): ResponseEntity<Unit> {
        val status = when (e) {
            is BoardNotFoundException, is UserNotFoundException -> 404
            is PostBadTitleException, is PostBadContentException -> 400
        }
        return ResponseEntity.status(status).build()
    }

}

data class ArticlePostRequest(
    val title: String,
    val content: String,
    val boardId: Long,
    val allowComments: Boolean,
    val isNotification: Boolean,
)
