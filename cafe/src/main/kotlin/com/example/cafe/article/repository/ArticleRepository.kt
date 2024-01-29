package com.example.cafe.article.repository

import jakarta.persistence.EntityManager
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional


interface ArticleRepository : JpaRepository<ArticleEntity, Long>, CustomArticleRepository{

    @Modifying
    @Transactional
    @Query(
            """
        UPDATE articles p SET p.viewCnt = p.viewCnt + 1 WHERE p.id = :articleId
    """
    )
    fun incrementViewCnt(articleId: Long)

    @Modifying
    @Transactional
    @Query(
        """
        UPDATE articles p SET p.likeCnt = p.likeCnt + 1 WHERE p.id = :articleId
    """
    )
    fun incrementLikeCnt(articleId: Long)

    @Modifying
    @Transactional
    @Query(
        """
        UPDATE articles p SET p.likeCnt = p.likeCnt - 1 WHERE p.id = :articleId
    """
    )
    fun decrementLikeCnt(articleId: Long)

    @Query("SELECT a FROM articles a JOIN FETCH a.user WHERE a.board.id = :boardId")
    fun findByBoardId(boardId: Long, pageable: Pageable): Page<ArticleEntity>

    @Query("SELECT al.article FROM article_likes al WHERE al.userId = :userId")
    fun findByArticleLikeUserId(userId: Long, pageable: Pageable): Page<ArticleEntity>

    @Query("SELECT a FROM articles a JOIN a.user WHERE a.user.id = :userId")
    fun findByUserId(userId: Long, pageable: Pageable): Page<ArticleEntity>

    @Query("SELECT distinct a FROM articles a JOIN a.comments ac WHERE ac.user.id = :userId")
    fun findByCommentUserId(userId: Long, pageable: Pageable): Page<ArticleEntity>

    @Query("SELECT a FROM articles a JOIN FETCH a.user JOIN FETCH a.board WHERE a.isNotification = true")
    fun findByIsNotificationTrue(): List<ArticleEntity>

    @Query("SELECT a FROM articles a JOIN FETCH a.user")
    override fun findAll(pageable: Pageable): Page<ArticleEntity>

    @Modifying
    @Transactional
    @Query(
        """
        UPDATE articles p SET p.commentCnt = p.commentCnt + 1 WHERE p.id = :articleId
    """
    )
    fun incrementCommentCnt(articleId: Long)

    @Modifying
    @Transactional
    @Query(
        """
        UPDATE articles p SET p.commentCnt = p.commentCnt - 1 WHERE p.id = :articleId
    """
    )
    fun decrementCommentCnt(articleId: Long)

    @Query("SELECT a FROM articles a JOIN FETCH a.user WHERE a.user.id = :userId")
    fun findFirst15ByUserId(userId: Long, pageable: Pageable): List<ArticleEntity>?
  
    fun findByTitleContaining(item: String): List<ArticleEntity>

    fun findByContentContaining(item: String): List<ArticleEntity>
  
    @Query(
        """
            SELECT a FROM articles a JOIN FETCH a.user WHERE a.user.nickname  LIKE %:item%
        """
    )
    fun findByUser_NicknameContaining(item: String): List<ArticleEntity>
}