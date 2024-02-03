package com.example.cafe.board.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface BoardLikeRepository : JpaRepository<BoardLikeEntity, Long> {
    fun findByUserIdAndBoardId(userId: Long, boardId: Long): BoardLikeEntity?

    @Modifying
    @Transactional
    @Query(
        """
        UPDATE boards b SET b.likeCnt = b.likeCnt + 1 WHERE b.id = :boardId
    """
    )
    fun incrementLikeCnt(boardId: Long)

    @Modifying
    @Transactional
    @Query(
        """
        UPDATE boards b SET b.likeCnt = b.likeCnt - 1 WHERE b.id = :boardId
    """
    )
    fun decrementLikeCnt(boardId: Long)
}
