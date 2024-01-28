package com.example.cafe.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
    fun findByNickname(nickname: String): UserEntity?
    @Modifying
    @Transactional
    @Query("""
        UPDATE users u SET u.articlesCount = u.articlesCount + 1 WHERE u.id = :userId
    """)
    fun incrementArticleCount(userId: Long)
    @Modifying
    @Transactional
    @Query(
        """
        UPDATE users u SET u.articlesCount = u.articlesCount - 1 WHERE u.id = :userId
    """
    )
    fun decrementArticleCount(userId: Long)

}
