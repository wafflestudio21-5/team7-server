package com.example.cafe.article.service

import java.time.LocalDateTime
import java.util.concurrent.Future

interface ArticleViewService {
    fun create(
               articleId: Long,
               userId: Long,
               createdAt: LocalDateTime = LocalDateTime.now()
    ) : Future<Boolean>
}


