package com.example.cafe.board.service

import com.example.cafe.article.service.ArticleBrief
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardService {
    fun get(): List<Board>

    fun getGroup(): List<BoardGroup>

    fun getArticles(boardId: Long, pageable: Pageable): Page<ArticleBrief>

    fun getNotification(boardId: Long): List<ArticleBrief>
}
