package com.example.cafe.user.repository

import com.example.cafe.article.repository.ArticleEntity
import com.example.cafe.comment.repository.CommentEntity
import com.example.cafe.comment.repository.RecommentEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.sql.Date
import java.time.LocalDateTime

@Entity(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val userId: String,
    val password: String,
    val username: String,
    val nickname: String = username,
    val email: String,
    val birthDate: Date,
    val phoneNumber: String,
    val rank: String = "씨앗",
    val registerDate: LocalDateTime,
    val visitCount: Long = 0L,
    val articlesCount: Long = 0L,
    val commentsCount: Long = 0L,
    @OneToMany(mappedBy = "user")
    val articles: List<ArticleEntity> = mutableListOf(),
    @OneToMany(mappedBy = "user")
    val comments: List<CommentEntity> = mutableListOf(),
    @OneToMany(mappedBy = "user")
    val recomments: List<RecommentEntity> = mutableListOf()
)
