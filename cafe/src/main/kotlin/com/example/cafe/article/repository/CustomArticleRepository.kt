package com.example.cafe.article.repository

import com.example.cafe.article.controller.HotTimeType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomArticleRepository {
    fun findTop200ByProperty(property: String, pageable: Pageable, hotTimeType: HotTimeType): Page<ArticleEntity>

}