package com.example.cafe.board.repository

import jakarta.persistence.*

@Entity(name = "board_likes")
class BoardLikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val boardId: Long,
    val userId: Long,
)
