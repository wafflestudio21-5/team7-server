package com.example.cafe.article.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.TemporalType
import org.apache.commons.lang3.time.DateUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Role
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date


class CustomArticleRepositoryImpl: CustomArticleRepository {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findTop200ByProperty(property: String, pageable: Pageable): Page<ArticleEntity> {

        val jpqlQuery = """SELECT a FROM articles a JOIN FETCH a.user 
            |WHERE a.createdAt >= :seven_days_ago 
            |ORDER BY CONCAT('a.', :property) DESC
            |""".trimMargin()
        val query = entityManager.createQuery(jpqlQuery, ArticleEntity::class.java)
            .setParameter("property", property)
            .setParameter("seven_days_ago", LocalDateTime.now().minusDays(7))
            .setMaxResults(200)
        val result = query.resultList
        result.sortBy { it.createdAt }
        return PageImpl(result, pageable, result.size.toLong())
    }
}