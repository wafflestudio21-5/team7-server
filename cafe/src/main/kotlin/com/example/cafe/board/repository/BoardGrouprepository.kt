package com.example.cafe.board.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardGroupRepository : JpaRepository<BoardGroupEntity, Long> {
    @Query("SELECT bg FROM board_groups bg JOIN FETCH bg.boards")
    fun find(): List<BoardGroupEntity>
}
