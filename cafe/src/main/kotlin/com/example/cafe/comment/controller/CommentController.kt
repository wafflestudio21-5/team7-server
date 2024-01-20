package com.example.cafe.comment.controller

import com.example.cafe.comment.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
class CommentController(
    private val commentService: CommentService,
) {
    @PostMapping("/api/v1/articles/{articleId}/comments")
    fun postComment(
        @RequestParam userId: String,
        @RequestParam content: String,
        @PathVariable articleId: Long,
    ) : PostCommentResponse {
        val comment = commentService.createComment(userId, articleId, content)
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
        @RequestParam userId: String,
        @RequestParam content: String,
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
    ) : UpdateCommentResponse {
        val comment = commentService.updateComment(commentId, userId, content)
        return UpdateCommentResponse(comment = comment)
    }

    @DeleteMapping("/api/v1/articles/{articleId}/comments/{commentId}")
    fun deleteComment(
        @RequestParam userId: String,
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
    ) {
        commentService.deleteComment(commentId, userId)
    }

    @PostMapping("/api/v1/articles/{articleId}/comments/{commentId}/recomments")
    fun postRecomment(
        @RequestParam userId: String,
        @RequestParam content: String,
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        ) : PostRecommentResponse {
        val recomment = commentService.createRecomment(userId, commentId, content)
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
        @RequestParam userId: String,
        @RequestParam content: String,
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        @PathVariable recommentId: Long,
        ) : UpdateRecommentResponse {
        val recomment = commentService.updateRecomment(recommentId, userId, content)
        return UpdateRecommentResponse(recomment = recomment)
    }

    @DeleteMapping("/api/v1/articles/{articleId}/comments/{commentId}/recomments/{recommentId}")
    fun deleteRecomment(
        @RequestParam userId: String,
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        @PathVariable recommentId: Long,
        ) {
        commentService.deleteRecomment(recommentId, userId)
    }

    @ExceptionHandler
    fun handleException(e: CommentException): ResponseEntity<Unit> {
        val status = when (e) {
            is CommentNotFoundException, is RecommentNotFoundException, is CommentUserNotFoundException, is CommentArticleNotFoundException -> 404
            is InvalidCommentUserException -> 403
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
