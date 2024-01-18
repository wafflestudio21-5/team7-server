package com.example.cafe.board.service

import com.example.cafe.board.repository.BoardLikeEntity
import com.example.cafe.board.repository.BoardLikeRepository
import com.example.cafe.board.repository.BoardRepository
import com.example.cafe.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class BoardLikeServiceImpl (
    private val boardLikeRepository: BoardLikeRepository,
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
) : BoardLikeService {
    override fun get(userId: String): List<Board> {
        val user = userRepository.findByUserId(userId) ?: throw UserNotFoundException()
        val boardLikeList = boardRepository.findByUserId(user.id)

        return boardLikeList.map {
            Board(
                id = it.id,
                name = it.name
            )
        }
    }

    override fun create(userId: String, boardId: Long) {
        val user = userRepository.findByUserId(userId) ?: throw UserNotFoundException()

        boardLikeRepository.save(
            BoardLikeEntity(
                userId = user.id,
                board = boardRepository.findById(boardId).get() //null?
            )
        )
    }

    override fun delete(userId: String, boardId: Long) {
        val user = userRepository.findByUserId(userId) ?: throw UserNotFoundException()
        val boardLike = boardLikeRepository.findByUserIdAndBoardId(user.id, boardId)

        boardLikeRepository.delete(boardLike)
    }

}
