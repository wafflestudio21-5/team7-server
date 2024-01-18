package com.example.cafe.article.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleRepository : JpaRepository<ArticleEntity, Long>{
    //fun findByName(name: String): ArticleEntity?
    //fun findByNameIn(names: List<String>): List<ArticleEntity>

    @Query("SELECT a FROM articles a JOIN FETCH a.user JOIN FETCH a.comments WHERE a.board.id = :boardId")
    fun findByBoardId(boardId: Long): List<ArticleEntity>
}