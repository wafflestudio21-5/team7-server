package com.example.cafe.user.service

import com.example.cafe.article.service.Article
import com.example.cafe.article.service.ArticleBrief
import java.time.LocalDate

data class UserInfo(
    val nickname: String,
    val rank: String,
    val introduction: String?,
    val visit_count: Long,
    val my_article_count: Long,
    val my_comment_count: Long,
    val register_date: LocalDate,
    val image: String?,
    val articles: List<ArticleBrief>
)
