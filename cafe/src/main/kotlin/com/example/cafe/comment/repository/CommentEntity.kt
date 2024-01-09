package com.example.cafe.comment.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import java.time.LocalDateTime
import com.example.cafe.user.repository.UserEntity
//import com.example.cafe.article.repository.ArticleEntity

@Entity(name = "comments")
class CommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val content: String,
    var createdAt: LocalDateTime,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserEntity,
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "article_id")
    //val article: ArticleEntity,
)
