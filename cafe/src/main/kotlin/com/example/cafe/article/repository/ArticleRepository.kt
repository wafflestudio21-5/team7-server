package com.example.cafe.article.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Page
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

    @Query("SELECT a FROM articles a JOIN FETCH a.user WHERE a.board.id = :boardId AND a.isNotification = true")
    fun findByBoardIdAndNotification(boardId: Long): List<ArticleEntity>

    @Query("SELECT al.article FROM article_likes al WHERE al.userId = :userId")
    fun findByArticleLikeUserId(userId: Long, pageable: Pageable): Page<ArticleEntity>

    fun findAllByOrderByViewCntDesc(): List<ArticleEntity>
    fun findAllByOrderByLikeCntDesc(): List<ArticleEntity>
    fun findAllByOrderByCommentCntDesc(): List<ArticleEntity>
  
    fun findAllByMinUserRankAllowedIn(ranks: MutableList<String>): List<ArticleEntity>

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

}