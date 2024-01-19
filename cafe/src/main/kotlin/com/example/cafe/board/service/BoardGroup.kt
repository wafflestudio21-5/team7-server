package com.example.cafe.board.service

data class BoardGroup (
    val id: Long,
    val name: String,
    val boards: List<Board>
)
