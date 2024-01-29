package com.example.cafe.article.controller

import com.example.cafe.article.service.*
import com.example.cafe.board.controller.ArticleBriefResponse
import com.example.cafe.board.service.Board
import com.example.cafe.user.service.Authenticated
import com.example.cafe.user.service.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

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
    ) : ArticlePostResponse {
        val newArticle = articleService.post(
            userId = user.id,
            title = request.title,
            content = request.content,
            boardId = request.boardId,
            allowComments = request.allowComments,
            isNotification = request.isNotification,
        )
        return ArticlePostResponse(
                id = newArticle.id,
                title = newArticle.title,
                content = newArticle.content,
                nickname = newArticle.user.nickname,
                createdAt = newArticle.createdAt,
        )
    }

    @PutMapping("/api/v1/articles/{articleId}/modify")
    fun modify(
            @RequestBody request: ArticleModifyRequest,
            @PathVariable articleId : Long,
            @Authenticated user: User,
    ): ArticleModifyResponse {
        val modifiedArticle = articleService.modify(
                userId = user.id,
                articleId = articleId,
                title = request.title,
                content = request.content,
                boardId = request.boardId,
                allowComments = request.allowComments,
                isNotification = request.isNotification,
        )
        return ArticleModifyResponse(
                id = modifiedArticle.id,
                title = modifiedArticle.title,
                content = modifiedArticle.content,
                nickname = modifiedArticle.user.nickname
        )
    }

    @DeleteMapping("/api/v1/articles/{articleId}")
    fun delete(
            @Authenticated user: User,
            @PathVariable articleId: Long,
    ){
        articleService.delete(
                articleId = articleId,
                userId = user.id
        )
    }


    @GetMapping("/api/v1/articles/{articleId}")
    fun getArticle(
        @PathVariable articleId: Long,
        user: User?,
    ): ArticleGetResponse {
        val article = articleService.get(articleId)
        val isLiked = if (user == null) {
            false
        } else {
            articleViewService.create(articleId = articleId, userId = user.id)
            articleLikeService.exists(articleId = articleId, userId = user.id)
        }
        return ArticleGetResponse(article, isLiked)
    }


    @PostMapping("/api/v1/articles/{articleId}/like")
    fun likeArticle(
        @PathVariable articleId: Long,
        @Authenticated user: User,
    ) {
        articleLikeService.create(articleId = articleId, userId = user.id)
    }

    @DeleteMapping("/api/v1/articles/{articleId}/like")
    fun unlikeArticle(
        @PathVariable articleId: Long,
        @Authenticated user: User,
    ) {
        articleLikeService.delete(articleId = articleId, userId = user.id)
    }

    @GetMapping("/api/v1/articles/hot")
    fun getHotArticles(
        @RequestParam(required = false, defaultValue = "VIEW") sortBy: ArticleService.HotSortType,
    ): ArticleBriefResponse {
        return ArticleBriefResponse(articleService.getHotArticles(sortBy))
    }

    @GetMapping("/api/v1/articles")
    fun getArticles(
        user: User?,
    ): ArticleBriefResponse {
        return ArticleBriefResponse(articleService.getArticles(
            userId = user?.id
        ))
    }

    @GetMapping("/api/v1/articles/notification")
    fun getNotification(
    ): ArticleBriefResponse {
        return ArticleBriefResponse(articleService.getNotification())
    }

    @ExceptionHandler
    fun handleException(e: ArticleException): ResponseEntity<Unit> {
        val status = when (e) {
            is BoardNotFoundException, is UserNotFoundException, is ArticleNotFoundException, is RankNotFoundException -> 404
            is PostBadTitleException, is PostBadContentException, is ArticleAlreadyLikedException, is ArticleNotLikedException -> 400
            is UnauthorizedModifyException -> 401
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

data class ArticleModifyRequest(
        val title: String,
        val content: String,
        val boardId: Long,
        val allowComments: Boolean,
        val isNotification: Boolean,
)

data class ArticlePostResponse(
        val id: Long,
        val title: String,
        val content: String,
        val nickname : String,
        val createdAt: LocalDateTime
)

data class ArticleModifyResponse(
        val id: Long,
        val title: String,
        val content: String,
        val nickname : String,
)

data class ArticleGetResponse(
        val article: Article,
        val isLiked: Boolean,
)

