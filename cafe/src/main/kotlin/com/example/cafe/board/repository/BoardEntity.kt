package com.example.cafe.board.repository

import com.example.cafe.article.repository.ArticleEntity
import jakarta.persistence.*

@Entity(name = "boards")
class BoardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    val group: BoardGroupEntity,
    val viewCnt: Long,
    val likeCnt: Long,
    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL])
    val articles: MutableList<ArticleEntity>,
    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL])
    val boardLikes: MutableList<BoardLikeEntity>,
)
