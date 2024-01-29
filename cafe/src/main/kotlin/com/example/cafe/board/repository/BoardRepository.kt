package com.example.cafe.board.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardRepository : JpaRepository<BoardEntity, Long> {
    @Query("SELECT b FROM boards b JOIN b.boardLikes bl WHERE bl.userId = :userId")
    fun findByUserId(userId: Long): List<BoardEntity>
}
