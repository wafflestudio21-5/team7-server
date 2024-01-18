package com.example.cafe.board.service

interface BoardLikeService {
    fun get(userId: String): List<Board>

    fun create(userId: String, boardId: Long)

    fun delete(userId: String, boardId: Long)
}
