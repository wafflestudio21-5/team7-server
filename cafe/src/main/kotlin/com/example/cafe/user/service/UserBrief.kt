package com.example.cafe.user.service

import java.time.LocalDate

data class UserBrief(
    val nickname: String,
    val rank: String,
    val visit_count: Long,
    val my_article_count: Long,
    val my_comment_count: Long,
    val register_date: LocalDate,
    val image: String?
)