package com.example.cafe.article.controller

import com.example.cafe.article.service.*
import com.example.cafe.board.controller.ArticleBriefPageResponse
import com.example.cafe.board.controller.ArticleBriefResponse
import com.example.cafe.board.service.Board
import com.example.cafe.user.service.Authenticated
import com.example.cafe.user.service.User
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
class ArticleController(
    private val articleService: ArticleService,
    private val articleViewService: ArticleViewService,
    private val articleLikeService: ArticleLikeService,
    private val articleSearchService: ArticleSearchService,
) {

    @PostMapping("/api/v1/articles/post")
    fun postArticle(
        @RequestBody request: ArticlePostRequest,
        @Authenticated user: User,
    ): ArticlePostResponse {
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
        @PathVariable articleId: Long,
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
    ) {
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
        @RequestParam("size", defaultValue = "20") size: Int,
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("sortBy", defaultValue = "viewCnt") sortBy: String,
        @RequestParam("time", defaultValue = "WEEK") time: String,
    ): ArticleBriefPageResponse {

        if (sortBy !in HotSortProperties) throw HotSortPropertyNotFoundException()
        try{
            val hotTimeType = HotTimeType.valueOf(time)
            val sort = Sort.by(Sort.Direction.DESC, "createdAt", "id")
            val pageRequest = PageRequest.of(page-1, size, sort)
            return ArticleBriefPageResponse(articleService.getHotArticles(sortBy, pageRequest, hotTimeType))
        }
        catch(e: IllegalArgumentException){
            throw HotTimeTypeNotFoundException()
        }
    }

    @GetMapping("/api/v1/articles")
    fun getArticles(
        @RequestParam("size", defaultValue = "15") size: Int,
        @RequestParam("page", defaultValue = "1") page: Int,
    ): ArticleBriefPageResponse {
        val sort = Sort.by(Sort.Direction.DESC, "createdAt", "id")
        val pageRequest = PageRequest.of(page-1, size, sort)
        return ArticleBriefPageResponse(
            articleService.getArticles(
                pageable = pageRequest
            )
        )
    }

    @GetMapping("/api/v1/articles/notification")
    fun getNotification(
    ): ArticleBriefResponse {
        return ArticleBriefResponse(articleService.getNotification())
    }

    @GetMapping("/api/v1/boards/{boardId}/search/{item}")
    fun searchArticlesInBoard(
        @RequestBody request: ArticleSearchRequest,
        @PathVariable boardId: Long,
        @PathVariable item: String,
        @RequestParam("size", defaultValue = "15") size: Int,
        @RequestParam("page", defaultValue = "1") page: Int,
    ): ArticleBriefPageResponse {
        val sort = Sort.by(Sort.Direction.DESC, "createdAt", "id")
        val pageRequest = PageRequest.of(page-1, size, sort)
        return ArticleBriefPageResponse(
            articleSearchService.search(
                item = item,
                boardId = boardId,
                searchCategory = request.searchCategory,
                startDate = request.startDate,
                endDate = request.endDate,
                wordInclude = request.wordInclude,
                wordExclude = request.wordExclude,
                pageable = pageRequest
            )
        )
    }

    @GetMapping("/api/v1/search/{item}")
    fun searchArticles(
        @RequestBody request: ArticleSearchRequest,
        @PathVariable item: String,
        @RequestParam("size", defaultValue = "15") size: Int,
        @RequestParam("page", defaultValue = "1") page: Int,
    ): ArticleBriefPageResponse {
        val sort = Sort.by(Sort.Direction.DESC, "createdAt", "id")
        val pageRequest = PageRequest.of(page-1, size, sort)
        return ArticleBriefPageResponse(
            articleSearchService.search(
                item = item,
                boardId = null,
                searchCategory = request.searchCategory,
                startDate = request.startDate,
                endDate = request.endDate,
                wordInclude = request.wordInclude,
                wordExclude = request.wordExclude,
                pageable = pageRequest
            )
        )
    }

    @ExceptionHandler
    fun handleException(e: ArticleException): ResponseEntity<Unit> {
        val status = when (e) {
            is BoardNotFoundException, is UserNotFoundException, is ArticleNotFoundException, is RankNotFoundException, is HotSortPropertyNotFoundException, is HotTimeTypeNotFoundException -> 404
            is PostBadTitleException, is PostBadContentException, is ArticleAlreadyLikedException, is ArticleNotLikedException, is BadCategoryException -> 400
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

data class ArticleSearchRequest(
    val searchCategory: Long,
    val startDate: String,
    val endDate: String,
    val wordInclude: String,
    val wordExclude: String
)

data class ArticlePostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val nickname: String,
    val createdAt: LocalDateTime
)

data class ArticleModifyResponse(
    val id: Long,
    val title: String,
    val content: String,
    val nickname: String,
)

data class ArticleGetResponse(
    val article: Article,
    val isLiked: Boolean,
)

val HotSortProperties = listOf("viewCnt", "likeCnt", "commentCnt")
enum class HotTimeType(val days: Long){
    WEEK(7),
    MONTH(30),
    ALL(15000)

}