package com.example.cafe.comment.service

import java.time.LocalDateTime

interface CommentService {
    fun getComment(id: Long): Comment
    fun getComments(articleId: Long): List<Comment>
    fun getRecomment(id: Long): Recomment
    fun getRecomments(commentId: Long): List<Recomment>
    fun createComment(userId: Long, articleId: Long, at: LocalDateTime = LocalDateTime.now())
    fun createRecomment(userId: Long, commentId: Long, at: LocalDateTime = LocalDateTime.now())
    fun modifyComment(id: Long, userId: Long, content: String)
    fun modifyRecomment(id: Long, userId: Long, content: String)
    fun deleteComment(id: Long, userId: Long)
    fun deleteRecomment(id: Long, userId: Long)
}
