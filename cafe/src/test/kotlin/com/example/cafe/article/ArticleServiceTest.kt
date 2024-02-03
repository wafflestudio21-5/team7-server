package com.example.cafe.article

import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.article.service.ArticleService
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ArticleServiceTest @Autowired constructor(
    private val articleService: ArticleService,
    private val articleRepository: ArticleRepository
) {
    @Test
    fun `공지 조회`() {
        val list = articleService.getNotification()

        assertThat(list.toList().map { it.id }).isEqualTo(listOf(
            1L, 14L, 17L
        ))
    }
}