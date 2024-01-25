package com.example.cafe.comment.controller

import com.example.cafe.comment.service.*
import com.example.cafe.user.service.Authenticated
import com.example.cafe.user.service.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
class CommentController(
    private val commentService: CommentService,
) {
    @PostMapping("/api/v1/articles/{articleId}/comments")
    fun postComment(
        @RequestParam content: String,
        @PathVariable articleId: Long,
        @Authenticated user: User
    ) : PostCommentResponse {
        val comment = commentService.createComment(user.id, articleId, content)
        return PostCommentResponse(
            commentId = comment.id,
            content = comment.content,
            lastModified = comment.lastModified,
            nickname = comment.nickname,
        )
    }

    @GetMapping("/api/v1/articles/{articleId}/comments")
    fun getComment(
        @PathVariable articleId: Long,
    ) : GetCommentResponse {
        val comments = commentService.getComments(articleId)
        return GetCommentResponse(comments = comments)
    }

    @PutMapping("/api/v1/articles/{articleId}/comments/{commentId}")
    fun updateComment(
        @RequestParam content: String,
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        @Authenticated user: User
    ) : UpdateCommentResponse {
        val comment = commentService.updateComment(commentId, user.id, content)
        return UpdateCommentResponse(comment = comment)
    }

    @DeleteMapping("/api/v1/articles/{articleId}/comments/{commentId}")
    fun deleteComment(
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        @Authenticated user: User
    ) {
        commentService.deleteComment(commentId, user.id, articleId)
    }

    @PostMapping("/api/v1/articles/{articleId}/comments/{commentId}/recomments")
    fun postRecomment(
        @RequestParam content: String,
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        @Authenticated user: User
        ) : PostRecommentResponse {
        val recomment = commentService.createRecomment(user.id, commentId, content)
        return PostRecommentResponse(
            recommentId = recomment.id,
            content = recomment.content,
            lastModified = recomment.lastModified,
            nickname = recomment.nickname,
        )
    }

    @GetMapping("/api/v1/articles/{articleId}/comments/{commentId}/recomments")
    fun getRecomment(
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        ) : GetRecommentResponse {
        val recomments = commentService.getRecomments(commentId)
        return GetRecommentResponse(recomments = recomments)
    }

    @PutMapping("/api/v1/articles/{articleId}/comments/{commentId}/recomments/{recommentId}")
    fun updateRecomment(
        @RequestParam content: String,
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        @PathVariable recommentId: Long,
        @Authenticated user: User
        ) : UpdateRecommentResponse {
        val recomment = commentService.updateRecomment(recommentId, user.id, content)
        return UpdateRecommentResponse(recomment = recomment)
    }

    @DeleteMapping("/api/v1/articles/{articleId}/comments/{commentId}/recomments/{recommentId}")
    fun deleteRecomment(
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        @PathVariable recommentId: Long,
        @Authenticated user: User
        ) {
        commentService.deleteRecomment(recommentId, user.id)
    }

    @ExceptionHandler
    fun handleException(e: CommentException): ResponseEntity<Unit> {
        val status = when (e) {
            is CommentNotFoundException, is RecommentNotFoundException, is CommentUserNotFoundException, is CommentArticleNotFoundException -> 404
            is InvalidCommentUserException -> 401
        }

        return ResponseEntity.status(status).build()
    }
}

data class PostCommentResponse(
    val commentId: Long,
    val content: String,
    val lastModified: LocalDateTime,
    val nickname: String,
)

data class GetCommentResponse(
    val comments: List<Comment>,
)

data class UpdateCommentResponse(
    val comment: Comment,
)

data class PostRecommentResponse(
    val recommentId: Long,
    val content: String,
    val lastModified: LocalDateTime,
    val nickname: String,
)

data class GetRecommentResponse(
    val recomments: List<Recomment>,
)

data class UpdateRecommentResponse(
    val recomment: Recomment,
)
