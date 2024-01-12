package com.example.cafe.article.repository

import org.springframework.data.jpa.repository.JpaRepository

interface ArticleViewRepository : JpaRepository<ArticleViewEntity, Long>{

}