package com.example.cafe.comment.repository

import com.example.cafe.article.service.ArticleBrief
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CommentRepository : JpaRepository<CommentEntity, Long> {
    fun findByContentContaining(item: String): List<CommentEntity>
    @Query(
        """
            SELECT c FROM comments c JOIN FETCH c.user WHERE c.user.nickname  LIKE %:item%
        """
    )
    fun findByUser_NicknameContaining(item: String): List<CommentEntity>
}
