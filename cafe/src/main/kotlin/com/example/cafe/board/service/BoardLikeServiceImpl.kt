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
    override fun exist(userId: Long, boardId: Long): Boolean {
        return boardLikeRepository.findByUserIdAndBoardId(userId, boardId) != null
    }

    override fun get(userId: String): List<Board> {
        val user = userRepository.findByUserId(userId) ?: throw BoardUserNotFoundException()
        val boardLikeList = boardRepository.findByUserId(user.id)

        return boardLikeList.map {
            Board(
                id = it.id,
                name = it.name
            )
        }
    }

    override fun create(userId: String, boardId: Long) {
        val user = userRepository.findByUserId(userId) ?: throw BoardUserNotFoundException()
        if (boardRepository.findById(boardId).isEmpty) {
            throw BoardNotFoundException()
        }

        if (exist(userId = user.id, boardId = boardId)) {
            throw BoardAlreadyLikedException()
        }

        boardLikeRepository.save(
            BoardLikeEntity(
                userId = user.id,
                board = boardRepository.findById(boardId).get()
            )
        )
    }

    override fun delete(userId: String, boardId: Long) {
        val user = userRepository.findByUserId(userId) ?: throw BoardUserNotFoundException()
        val boardLike = boardLikeRepository.findByUserIdAndBoardId(user.id, boardId)
            ?: throw BoardNeverLikedException()

        boardLikeRepository.delete(boardLike)
    }

}
