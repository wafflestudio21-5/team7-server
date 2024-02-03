package com.example.cafe.board.service

interface BoardLikeService {
    fun exist(userId: Long, boardId: Long): Boolean

    fun get(userId: Long): List<Board>

    fun create(userId: Long, boardId: Long)

    fun delete(userId: Long, boardId: Long)
}
