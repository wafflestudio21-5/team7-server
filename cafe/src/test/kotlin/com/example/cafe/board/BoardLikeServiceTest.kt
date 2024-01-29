package com.example.cafe.board

import com.example.cafe.board.repository.BoardLikeRepository
import com.example.cafe.board.repository.BoardRepository
import com.example.cafe.board.service.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BoardLikeServiceTest @Autowired constructor(
    private val boardLikeService: BoardLikeService,
    private val boardRepository: BoardRepository,
    private val boardLikeRepository: BoardLikeRepository
) {
    @BeforeEach
    fun before() {
        boardRepository.findAll()
            .forEach {
                it.likeCnt = 0
                boardRepository.save(it)
            }

        boardLikeRepository.deleteAll()
    }

    @Test
    fun `존재하지 않는 게시판에 좋아요를 누를 수 없음`() {
        assertThrows<BoardNotFoundException> {
            boardLikeService.create(
                userId = 1,
                boardId = 404404
            )
        }
    }

    @Test
    fun `이미 좋아요한 게시판에 다시 좋아요를 누를 수 없음`() {
        boardLikeService.create(
            userId = 1,
            boardId = 1
        )

        assertThrows<BoardAlreadyLikedException> {
            boardLikeService.create(
                userId = 1,
                boardId = 1
            )
        }
    }

    @Test
    fun `좋아요하지 않은 게시판에 좋아요 취소를 누를 수 없음`() {
        assertThrows<BoardNeverLikedException> {
            boardLikeService.delete(
                userId = 1,
                boardId = 1
            )
        }
    }

    @Test
    fun `유저를 찾을 수 없음`() {
        assertThrows<BoardUserNotFoundException> {
            boardLikeService.create(
                userId = 404404,
                boardId = 1
            )
        }

        assertThrows<BoardUserNotFoundException> {
            boardLikeService.delete(
                userId = 404404,
                boardId = 1
            )
        }

        assertThrows<BoardUserNotFoundException> {
            boardLikeService.get(
                userId = 404404
            )
        }
    }

    @Test
    fun `likeCnt 증가`() {
        boardLikeService.create(1, 1)
        boardLikeService.create(1, 2)
        boardLikeService.create(2, 1)

        val board1 = boardRepository.findById(1).get()
        val board2 = boardRepository.findById(2).get()
        val board3 = boardRepository.findById(3).get()

        assertThat(board1.likeCnt).isEqualTo(2)
        assertThat(board2.likeCnt).isEqualTo(1)
        assertThat(board3.likeCnt).isEqualTo(0)
    }

    @Test
    fun `likeCnt 감소`() {
        boardLikeService.create(1, 1)
        boardLikeService.create(1, 2)
        boardLikeService.create(2, 1)
        boardLikeService.delete(1, 1)
        boardLikeService.delete(1, 2)

        val board1 = boardRepository.findById(1).get()
        val board2 = boardRepository.findById(2).get()
        val board3 = boardRepository.findById(3).get()

        assertThat(board1.likeCnt).isEqualTo(1)
        assertThat(board2.likeCnt).isEqualTo(0)
        assertThat(board3.likeCnt).isEqualTo(0)
    }

    @Test
    fun `좋아요한 게시판 조회`() {
        val list0 = boardLikeService.get(1)
        assertThat(list0.size).isEqualTo(0)


        boardLikeService.create(1, 1)
        boardLikeService.create(1, 2)
        boardLikeService.create(2, 1)
        val list1 = boardLikeService.get(1)
        val list2 = boardLikeService.get(2)
        assertThat(list1.size).isEqualTo(2)
        assertThat(list2.size).isEqualTo(1)
        assertThat(list1.map { it.id }).isEqualTo(listOf(1L, 2L))
        assertThat(list2.map { it.id }).isEqualTo(listOf(1L))


        boardLikeService.delete(1, 1)
        val list3 = boardLikeService.get(1)
        assertThat(list3.size).isEqualTo(1)
        assertThat(list3.map { it.id }).isEqualTo(listOf(2L))
    }

}
