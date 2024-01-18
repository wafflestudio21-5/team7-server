package com.example.cafe.board.service

import com.example.cafe.article.service.Article
import com.example.cafe.article.service.ArticleBrief

interface BoardService {
    fun get(): List<Board>

    fun getGroup(): List<BoardGroup>

    fun getArticles(boardId: Long): List<ArticleBrief>
}
