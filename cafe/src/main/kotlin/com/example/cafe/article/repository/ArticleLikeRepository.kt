package com.example.cafe.article.repository

import org.springframework.data.jpa.repository.JpaRepository

interface ArticleLikeRepository : JpaRepository<ArticleLikeEntity, Long> {
}