package com.example.cafe.comment.repository

import java.time.LocalDateTime
import com.example.cafe.user.repository.UserEntity
import com.example.cafe.article.repository.ArticleEntity
import jakarta.persistence.*

@Entity(name = "comments")
class CommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var content: String,
    var lastModified: LocalDateTime,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    val article: ArticleEntity,
    @OneToMany(mappedBy = "comment", cascade = [CascadeType.REMOVE])
    val recomments: MutableList<RecommentEntity> = mutableListOf(),
)
