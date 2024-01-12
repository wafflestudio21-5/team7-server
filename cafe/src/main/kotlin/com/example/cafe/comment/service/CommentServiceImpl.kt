package com.example.cafe.comment.service

import com.example.cafe.comment.repository.CommentEntity
import com.example.cafe.comment.repository.CommentRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommentServiceImpl (
    private val commentRepository: CommentRepository,
) : CommentService {
    override fun getComment(id: Long): Comment {
        TODO()
    }

    override fun getComments(articleId: Long): List<Comment> {
        TODO()
    }

    override fun getRecomment(id: Long): Recomment {
        TODO()
    }

    override fun getRecomments(commentId: Long): List<Recomment> {
        TODO()
    }

    override fun createComment(userId: Long, articleId: Long, at: LocalDateTime) {
        TODO()
    }

    override fun createRecomment(userId: Long, commentId: Long, at: LocalDateTime) {
        TODO()
    }

    override fun modifyComment(id: Long, userId: Long, content: String) {
        TODO()
    }

    override fun modifyRecomment(id: Long, userId: Long, content: String) {
        TODO()
    }

    override fun deleteComment(id: Long, userId: Long) {
        TODO()
    }

    override fun deleteRecomment(id: Long, userId: Long) {
        TODO()
    }
}
