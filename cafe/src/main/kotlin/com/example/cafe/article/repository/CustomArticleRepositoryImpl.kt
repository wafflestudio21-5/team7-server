package com.example.cafe.article.repository

import com.example.cafe.article.controller.HotTimeType
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
import kotlin.math.min


class CustomArticleRepositoryImpl: CustomArticleRepository {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findTop200ByProperty(property: String, pageable: Pageable, hotTimeType: HotTimeType): Page<ArticleEntity> {

        val jpqlQuery = """SELECT a FROM articles a JOIN FETCH a.user 
            |WHERE a.createdAt >= :days_ago 
            |ORDER BY a.$property DESC, a.createdAt DESC
            |""".trimMargin()

        val query = entityManager.createQuery(jpqlQuery, ArticleEntity::class.java)
            .setParameter("days_ago", LocalDateTime.now().minusDays(hotTimeType.days))
            .setMaxResults(200)
        val result = query.resultList
        if(property=="viewCnt"){
            result.sortByDescending{ it.createdAt }
        }
        val startIndex = pageable.pageNumber * pageable.pageSize
        val endIndex = min(startIndex + pageable.pageSize, result.size)
        return PageImpl(result.subList(startIndex, endIndex), pageable, result.size.toLong())
    }
}