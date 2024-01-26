package com.example.cafe.board

import com.example.cafe.board.repository.BoardRepository
import com.example.cafe.board.service.BoardLikeService
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BoardLikeServiceTest @Autowired constructor(
    private val boardLikeService: BoardLikeService,
    private val boardRepository: BoardRepository
) {
    @BeforeEach
    fun init() {
        boardLikeService.create(1, 1)
        boardLikeService.create(1, 2)
        boardLikeService.create(2, 1)
    }

    @Test
    fun `likeCnt 증가`() {
        val board1 = boardRepository.findById(1).get()
        val board2 = boardRepository.findById(2).get()
        val board3 = boardRepository.findById(3).get()

        assertThat(board1.likeCnt).isEqualTo(2)
        assertThat(board2.likeCnt).isEqualTo(1)
        assertThat(board3.likeCnt).isEqualTo(0)

        boardLikeService.delete(1, 1)
        boardLikeService.delete(1, 2)
        boardLikeService.delete(2, 1)
    }

    @Test
    fun `likeCnt 감소`() {
        boardLikeService.delete(1, 1)
        boardLikeService.delete(1, 2)

        val board1 = boardRepository.findById(1).get()
        val board2 = boardRepository.findById(2).get()
        val board3 = boardRepository.findById(3).get()

        assertThat(board1.likeCnt).isEqualTo(1)
        assertThat(board2.likeCnt).isEqualTo(0)
        assertThat(board3.likeCnt).isEqualTo(0)
        boardLikeService.delete(2, 1)
    }

}