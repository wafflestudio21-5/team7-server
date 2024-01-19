package com.example.cafe.comment.service

import java.time.LocalDateTime

interface CommentService {
    fun getComments(articleId: Long): List<Comment>
    fun getRecomments(commentId: Long): List<Recomment>
    fun createComment(userId: String, articleId: Long, content: String, at: LocalDateTime = LocalDateTime.now()): Comment
    fun createRecomment(userId: String, commentId: Long, content: String, at: LocalDateTime = LocalDateTime.now()): Recomment
    fun updateComment(id: Long, userId: String, content: String, at: LocalDateTime = LocalDateTime.now()) : Comment
    fun updateRecomment(id: Long, userId: String, content: String, at: LocalDateTime = LocalDateTime.now()) : Recomment
    fun deleteComment(id: Long, userId: String)
    fun deleteRecomment(id: Long, userId: String)
}
