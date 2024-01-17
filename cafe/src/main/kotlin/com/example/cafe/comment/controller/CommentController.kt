package com.example.cafe.comment.controller

import com.example.cafe.comment.service.Comment
import com.example.cafe.comment.service.CommentService
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
    val comment:Comment,
)
