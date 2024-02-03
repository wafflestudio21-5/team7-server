package com.example.cafe.user.repository

import com.example.cafe.article.repository.ArticleEntity
import com.example.cafe.comment.repository.CommentEntity
import com.example.cafe.comment.repository.RecommentEntity
import com.example.cafe.user.service.User
import jakarta.persistence.*
import java.sql.Date
import java.time.LocalDate

@Entity(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val username: String? = null,
    val password: String? = null,
    val snsId: String? = null,
    val name: String,
    var nickname: String,
    val email: String? = null,
    val birthDate: Date,
    val phoneNumber: String,
    val rank: String = User.Rank.USER.name,
    val registerDate: LocalDate,
    var introduction: String? = null,
    var image: String? = null,
    val visitCount: Long = 0L,
    val articlesCount: Long = 0L,
    var commentsCount: Long = 0L,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val articles: List<ArticleEntity> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val comments: List<CommentEntity> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val recomments: List<RecommentEntity> = mutableListOf()
)
