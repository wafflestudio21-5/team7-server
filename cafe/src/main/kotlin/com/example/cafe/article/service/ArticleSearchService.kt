package com.example.cafe.article.service

interface ArticleSearchService {
    fun search(
        item: String,
        boardId: Long?,
        searchCategory : Long,
        startDate : String,
        endDate: String,
        wordInclude: String,
        wordExclude: String
    ): List<ArticleBrief>
}