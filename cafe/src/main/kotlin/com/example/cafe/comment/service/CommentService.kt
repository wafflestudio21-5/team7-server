package com.example.cafe.comment.service

import java.time.LocalDateTime

interface CommentService {
    fun getComments(articleId: Long): List<Comment>
    fun getRecomments(commentId: Long): List<Recomment>
    fun createComment(userId: Long, articleId: Long, content: String, at: LocalDateTime = LocalDateTime.now()): Comment
    fun createRecomment(userId: Long, commentId: Long, content: String, at: LocalDateTime = LocalDateTime.now()): Recomment
    fun updateComment(id: Long, userId: Long, content: String, at: LocalDateTime = LocalDateTime.now()) : Comment
    fun updateRecomment(id: Long, userId: Long, content: String, at: LocalDateTime = LocalDateTime.now()) : Recomment
    fun deleteComment(id: Long, userId: Long, articleId: Long)
    fun deleteRecomment(id: Long, userId: Long)
}
