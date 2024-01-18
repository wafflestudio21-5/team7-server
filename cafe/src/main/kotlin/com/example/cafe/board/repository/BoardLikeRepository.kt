package com.example.cafe.board.repository

import org.springframework.data.jpa.repository.JpaRepository

interface BoardLikeRepository : JpaRepository<BoardLikeEntity, Long> {
    fun findByUserIdAndBoardId(userId: Long, boardId: Long): BoardLikeEntity
}
