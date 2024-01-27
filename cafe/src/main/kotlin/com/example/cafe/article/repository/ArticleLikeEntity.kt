package com.example.cafe.article.repository

import jakarta.persistence.*

@Entity(name = "article_likes")
class ArticleLikeEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "article_id")
        val article: ArticleEntity,
        val userId: Long,
)