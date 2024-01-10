package com.example.cafe.board.repository

import jakarta.persistence.*

@Entity(name = "board_likes")
class BoardLikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val board_id: Long,
    val user_id: Long,
)
