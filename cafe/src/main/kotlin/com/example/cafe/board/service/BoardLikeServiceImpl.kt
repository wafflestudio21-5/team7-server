package com.example.cafe.board.service

import com.example.cafe.board.repository.BoardLikeEntity
import com.example.cafe.board.repository.BoardLikeRepository
import com.example.cafe.board.repository.BoardRepository
import com.example.cafe.user.repository.UserRepository
import com.example.cafe.user.service.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardLikeServiceImpl (
    private val boardLikeRepository: BoardLikeRepository,
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
) : BoardLikeService {
    override fun exist(userId: Long, boardId: Long): Boolean {
        return boardLikeRepository.findByUserIdAndBoardId(userId, boardId) != null
    }

    override fun get(userId: Long): List<Board> {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException() }
        val boardLikeList = boardRepository.findByUserId(user.id)

        return boardLikeList.map {
            Board(
                id = it.id,
                name = it.name
            )
        }
    }

    @Transactional
    override fun create(userId: Long, boardId: Long) {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException() }
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

        boardLikeRepository.incrementLikeCnt(boardId)
    }

    @Transactional
    override fun delete(userId: Long, boardId: Long) {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException() }
        val boardLike = boardLikeRepository.findByUserIdAndBoardId(user.id, boardId)
            ?: throw BoardNeverLikedException()

        boardLikeRepository.delete(boardLike)

        boardLikeRepository.decrementLikeCnt(boardId)
    }

}
