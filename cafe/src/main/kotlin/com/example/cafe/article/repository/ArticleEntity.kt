package com.example.cafe.article.repository

import com.example.cafe.board.repository.BoardEntity
import com.example.cafe.board.repository.BoardLikeEntity
import com.example.cafe.comment.repository.CommentEntity
import com.example.cafe.user.repository.UserEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "articles")
class ArticleEntity (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long = 0,
        val title : String,
        val content : String,
        val createdAt : LocalDateTime,
        val viewCnt : Long = 0,
        val likeCnt : Long = 0,
        var commentCnt: Long = 0,
        val minUserRankAllowed: String = "씨앗",
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        val user : UserEntity,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "board_id")
        val board : BoardEntity,
        val allowComments : Boolean,
        val isNotification : Boolean,
        @OneToMany(mappedBy = "article", cascade = [CascadeType.REMOVE])
        val comments: MutableList<CommentEntity> = mutableListOf(),
        @OneToMany(mappedBy = "article", cascade = [CascadeType.ALL])
        val articleLikes: MutableList<ArticleLikeEntity> = mutableListOf(),
)