package com.example.cafe.article.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional


interface ArticleRepository : JpaRepository<ArticleEntity, Long>{
    //fun findByName(name: String): ArticleEntity?
    //fun findByNameIn(names: List<String>): List<ArticleEntity>
    @Modifying
    @Transactional
    @Query(
            """
        UPDATE articles p SET p.viewCnt = p.viewCnt + 1 WHERE p.id = :articleId
    """
    )
    fun incrementViewCnt(articleId: Long)

    @Query("SELECT a FROM articles a JOIN FETCH a.user JOIN FETCH a.comments WHERE a.board.id = :boardId")
    fun findByBoardId(boardId: Long): List<ArticleEntity>

}