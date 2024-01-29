package com.example.cafe.article.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomArticleRepository {
    fun findTop200ByProperty(property: String, pageable: Pageable): Page<ArticleEntity>

}