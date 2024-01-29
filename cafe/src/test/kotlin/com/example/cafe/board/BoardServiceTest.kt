package com.example.cafe.board

import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.board.repository.BoardLikeRepository
import com.example.cafe.board.service.BoardNotFoundException
import com.example.cafe.board.service.BoardService
import com.example.cafe.board.service.IllegalSortArgumentException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class BoardServiceTest @Autowired constructor(
    private val boardService: BoardService,
    private val boardLikeRepository: BoardLikeRepository,
    private val articleRepository: ArticleRepository,
) {
    @Test
    fun `존재하지 않는 게시판`() {
        boardService.getArticles(
            boardId = 1,
            page = 0,
            size = 15,
            sort = listOf("createdAt", "desc")
        )

        assertThrows<BoardNotFoundException> {
            boardService.getArticles(
                boardId = 404404,
                page = 0,
                size = 15,
                sort = listOf("createdAt", "desc")
            )
        }
    }

    @Test
    fun `잘못된 sort 파라미터`() {
        boardService.getArticles(
            boardId = 1,
            page = 0,
            size = 15,
            sort = listOf("createdAt", "desc")
        )

        assertThrows<IllegalSortArgumentException> {
            boardService.getArticles(
                boardId = 1,
                page = 0,
                size = 15,
                sort = listOf("wrong property", "desc")
            )
        }

        assertThrows<IllegalSortArgumentException> {
            boardService.getArticles(
                boardId = 1,
                page = 0,
                size = 15,
                sort = listOf("createdAt", "wrong direction")
            )
        }

        assertThrows<IllegalSortArgumentException> {
            boardService.getArticles(
                boardId = 1,
                page = 0,
                size = 15,
                sort = listOf("no param")
            )
        }

        assertThrows<IllegalSortArgumentException> {
            boardService.getArticles(
                boardId = 1,
                page = 0,
                size = 15,
                sort = listOf("too", "many", "param")
            )
        }

    }

    @Test
    fun `전체게시판 조회`() {
        val list = boardService.get()
        assertThat(list.toList().map { it.id }).isEqualTo(listOf(
            1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L
        ))
    }

    @Transactional
    @Test
    fun `게시판 그룹, 인기게시판 조회`() {
        for (i: Int in 1..3)
            boardLikeRepository.incrementLikeCnt(2)
        for (i: Int in 1..3)
            boardLikeRepository.incrementLikeCnt(3)
        for (i: Int in 1..2)
            boardLikeRepository.incrementLikeCnt(5)
        boardLikeRepository.incrementLikeCnt(4)

        val list = boardService.getGroup()
        assertThat(list[0].boards.map { it.id }).isEqualTo(listOf(
            1L, 2L, 3L
        ))
        assertThat(list[1].boards.map { it.id }).isEqualTo(listOf(
            4L, 6L, 7L
        ))
        assertThat(list[2].boards.map { it.id }).isEqualTo(listOf(
            5L, 8L
        ))

        assertThat(list[0].boards.map { it.isHot }).isEqualTo(listOf(
            false, true, true
        ))
        assertThat(list[1].boards.map { it.isHot }).isEqualTo(listOf(
            false, false, false
        ))
        assertThat(list[2].boards.map { it.isHot }).isEqualTo(listOf(
            true, false
        ))
    }

    @Test
    fun `게시판별 게시글 조회`() {
        val list1 = boardService.getArticles(1, 0, 15, listOf("createdAt", "desc"))
        val list2 = boardService.getArticles(2, 0, 15, listOf("createdAt", "desc"))
        val list3 = boardService.getArticles(3, 0, 15, listOf("createdAt", "desc"))

        assertThat(list1.toList().map { it.id }).isEqualTo(listOf(
            16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L
        ))
        assertThat(list2.toList().map { it.id }).isEqualTo(listOf(19L, 18L, 17L))
        assertThat(list3.toList().map { it.id }).isEqualTo(emptyList<Long>())
    }

    @Test
    fun `페이지네이션 size 테스트`() {
        val list1 = boardService.getArticles(1, 0, 20, listOf("createdAt", "desc"))
        val list2 = boardService.getArticles(1, 0, 15, listOf("createdAt", "desc"))
        val list3 = boardService.getArticles(1, 0, 5, listOf("createdAt", "desc"))

        assertThat(list1.toList().size).isEqualTo(16)
        assertThat(list2.toList().size).isEqualTo(15)
        assertThat(list3.toList().size).isEqualTo(5)

        assertThat(list1.toList().map { it.id }).isEqualTo(listOf(
            16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L
        ))
        assertThat(list2.toList().map { it.id }).isEqualTo(listOf(
            16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L
        ))
        assertThat(list3.toList().map { it.id }).isEqualTo(listOf(
            16L, 15L, 14L, 13L, 12L
        ))
    }

    @Test
    fun `페이지네이션 page 테스트`() {
        val list1 = boardService.getArticles(1, 0, 15, listOf("createdAt", "desc"))
        val list2 = boardService.getArticles(1, 1, 15, listOf("createdAt", "desc"))
        assertThat(list1.toList().map { it.id }).isEqualTo(listOf(
            16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L
        ))
        assertThat(list2.toList().map { it.id }).isEqualTo(listOf(
            1L
        ))

        val list3 = boardService.getArticles(1, 0, 5, listOf("createdAt", "desc"))
        val list4 = boardService.getArticles(1, 1, 5, listOf("createdAt", "desc"))
        val list5 = boardService.getArticles(1, 2, 5, listOf("createdAt", "desc"))
        val list6 = boardService.getArticles(1, 3, 5, listOf("createdAt", "desc"))
        assertThat(list3.toList().map { it.id }).isEqualTo(listOf(
            16L, 15L, 14L, 13L, 12L
        ))
        assertThat(list4.toList().map { it.id }).isEqualTo(listOf(
            11L, 10L, 9L, 8L, 7L
        ))
        assertThat(list5.toList().map { it.id }).isEqualTo(listOf(
            6L, 5L, 4L, 3L, 2L
        ))
        assertThat(list6.toList().map { it.id }).isEqualTo(listOf(
            1L
        ))
    }

    //정렬 테스트
    //sort property: "createdAt", "title", "likeCnt", "viewCnt", "commentCnt"
    @Test
    fun `시간순 정렬`() {
        val list1 = boardService.getArticles(1, 0, 15, listOf("createdAt", "desc"))
        val list2 = boardService.getArticles(1, 0, 15, listOf("createdAt", "asc"))
        assertThat(list1.toList().map { it.id }).isEqualTo(listOf(
            16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L
        ))
        assertThat(list2.toList().map { it.id }).isEqualTo(listOf(
            1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 16L, 15L
        ))

    }

    @Test
    fun `제목 사전순 정렬`() {
        val list1 = boardService.getArticles(1, 0, 15, listOf("title", "desc"))
        val list2 = boardService.getArticles(1, 0, 15, listOf("title", "asc"))
        assertThat(list1.toList().map { it.id }).isEqualTo(listOf(
            14L, 15L, 16L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L
        ))
        assertThat(list2.toList().map { it.id }).isEqualTo(listOf(
            3L, 2L, 1L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 16L, 15L
        ))

    }

    @Transactional
    @Test
    fun `좋아요순 정렬`() {
        for (i: Int in 1..3)
            articleRepository.incrementLikeCnt(1)
        for (i: Int in 1..3)
            articleRepository.incrementLikeCnt(2)
        for (i: Int in 1..2)
            articleRepository.incrementLikeCnt(3)
        for (i: Int in 1..1)
            articleRepository.incrementLikeCnt(4)

        val list1 = boardService.getArticles(1, 0, 15, listOf("likeCnt", "desc"))
        val list2 = boardService.getArticles(1, 0, 15, listOf("likeCnt", "asc"))
        assertThat(list1.toList().map { it.id }).isEqualTo(listOf(
            2L, 1L, 3L, 4L, 16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L
        ))
        assertThat(list2.toList().map { it.id }).isEqualTo(listOf(
            16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L
        ))
    }

    @Transactional
    @Test
    fun `조회순 정렬`() {
        for (i: Int in 1..3)
            articleRepository.incrementViewCnt(1)
        for (i: Int in 1..3)
            articleRepository.incrementViewCnt(2)
        for (i: Int in 1..2)
            articleRepository.incrementViewCnt(3)
        for (i: Int in 1..1)
            articleRepository.incrementViewCnt(4)

        val list1 = boardService.getArticles(1, 0, 15, listOf("viewCnt", "desc"))
        val list2 = boardService.getArticles(1, 0, 15, listOf("viewCnt", "asc"))
        assertThat(list1.toList().map { it.id }).isEqualTo(listOf(
            2L, 1L, 3L, 4L, 16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L
        ))
        assertThat(list2.toList().map { it.id }).isEqualTo(listOf(
            16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L
        ))
    }

    @Transactional
    @Test
    fun `댓글순 정렬`() {
        for (i: Int in 1..3)
            articleRepository.incrementCommentCnt(1)
        for (i: Int in 1..3)
            articleRepository.incrementCommentCnt(2)
        for (i: Int in 1..2)
            articleRepository.incrementCommentCnt(3)
        for (i: Int in 1..1)
            articleRepository.incrementCommentCnt(4)

        val list1 = boardService.getArticles(1, 0, 15, listOf("commentCnt", "desc"))
        val list2 = boardService.getArticles(1, 0, 15, listOf("commentCnt", "asc"))
        assertThat(list1.toList().map { it.id }).isEqualTo(listOf(
            2L, 1L, 3L, 4L, 16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L
        ))
        assertThat(list2.toList().map { it.id }).isEqualTo(listOf(
            16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L
        ))
    }
}
