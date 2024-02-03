package com.example.cafe.article.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ArticleSearchService {
    fun search(
        item: String,
        boardId: Long?,
        searchCategory : Long,
        startDate : String,
        endDate: String,
        wordInclude: String,
        wordExclude: String,
        pageable: Pageable
    ): Page<ArticleBrief>
}