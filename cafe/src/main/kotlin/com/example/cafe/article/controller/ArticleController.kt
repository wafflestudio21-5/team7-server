package com.example.cafe.article.controller

import com.example.cafe.article.service.*
import com.example.cafe.board.service.Board
import com.example.cafe.user.service.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ArticleController(
    private val articleService: ArticleService,
    private val articleViewService: ArticleViewService,
    private val articleLikeService: ArticleLikeService,
) {

    @PostMapping("/api/v1/articles/post")
    fun postArticle(
            @RequestBody request: ArticlePostRequest,
            @Authenticated user: User,
    ) {
        articleService.post(
            userId = user.userId,
            title = request.title,
            content = request.content,
            boardId = request.boardId,
            allowComments = request.allowComments,
            isNotification = request.isNotification,
        )
    }

    @GetMapping("/api/v1/articles/{articleId}")
    fun getArticle(
        @PathVariable articleId: Long,
        user: User?,
    ): ArticleResponse {
        val article = articleService.get(articleId)
        val isLiked = if (user == null) {
            false
        } else {
            articleViewService.create(articleId = articleId, userId = user.userId)
            articleLikeService.exists(articleId = articleId, userId = user.userId)
        }
        return ArticleResponse(article, isLiked)
    }


    @PostMapping("/api/v1/articles/{articleId}/like")
    fun likeArticle(
        @PathVariable articleId: Long,
        @Authenticated user: User,
    ) {
        articleLikeService.create(articleId = articleId, userId = user.userId)
    }
    @DeleteMapping("/api/v1/articles/{articleId}/like")
    fun unlikeArticle(
        @PathVariable articleId: Long,
        @Authenticated user: User,
    ) {
        articleLikeService.delete(articleId = articleId, userId = user.userId)
    }



    @ExceptionHandler
    fun handleException(e: ArticleException): ResponseEntity<Unit> {
        val status = when (e) {
            is BoardNotFoundException, is UserNotFoundException, is ArticleNotFoundException -> 404
            is PostBadTitleException, is PostBadContentException, is ArticleAlreadyLikedException, is ArticleNotLikedException -> 400
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

data class ArticleResponse(
        val article: Article,
        val isLiked: Boolean,
)
