package com.example.cafe.board.repository

import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<BoardEntity, Long> {

}
