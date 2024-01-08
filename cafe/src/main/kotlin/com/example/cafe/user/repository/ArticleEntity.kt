package com.example.cafe.user.repository

import jakarta.persistence.*
import java.sql.Timestamp

@Entity(name = "articles")
class ArticleEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(length = 255, nullable = false)
    val title: String,
    @ManyToOne
    @JoinColumn(name = "author_id",
        referencedColumnName = "id",
        referencedTableName = "users")
    val authorId: Long,
    val board_id: Long,
    @Column(length = 10000, nullable = false)
    val content: String,
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Timestamp, //assigned via application not the dbms
    val view_cnt: Long = 0L,
    val like_cnt: Long = 0L,
    val allow_comment: Boolean = true,
    val is_notification: Boolean = false
)