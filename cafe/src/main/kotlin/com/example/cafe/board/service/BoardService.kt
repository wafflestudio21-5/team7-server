package com.example.cafe.board.service

import com.example.cafe.article.service.ArticleBrief
import org.springframework.data.domain.Page

interface BoardService {
    fun get(): List<Board>

    fun getGroup(): List<BoardGroup>

    fun getArticles(boardId: Long, page: Int, size: Int, sort: List<String>): Page<ArticleBrief>

}
