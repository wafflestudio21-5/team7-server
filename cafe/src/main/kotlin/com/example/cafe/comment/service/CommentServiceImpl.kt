package com.example.cafe.comment.service

import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.comment.repository.CommentEntity
import com.example.cafe.comment.repository.CommentRepository
import com.example.cafe.comment.repository.RecommentEntity
import com.example.cafe.comment.repository.RecommentRepository
import com.example.cafe.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommentServiceImpl (
    private val commentRepository: CommentRepository,
    private val recommentRepository: RecommentRepository,
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
) : CommentService {
    override fun getComments(articleId: Long): List<Comment> {
        val article = articleRepository.findByIdOrNull(articleId) ?: throw CommentArticleNotFoundException()
        val comments = article.comments.map{ commentEntity ->
            Comment(
                id = commentEntity.id,
                content = commentEntity.content,
                lastModified = commentEntity.lastModified,
                nickname = commentEntity.user.nickname,
                recomments = commentEntity.recomments.map { recommentEntity ->
                    Recomment(
                        id = recommentEntity.id,
                        content = recommentEntity.content,
                        lastModified = recommentEntity.lastModified,
                        nickname = recommentEntity.user.nickname,
                    )
                }
            )
        }
        return comments
    }

    override fun getRecomments(commentId: Long): List<Recomment> {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw CommentNotFoundException()
        val recomments = comment.recomments.map{ recommentEntity ->
            Recomment(
                id = recommentEntity.id,
                content = recommentEntity.content,
                lastModified = recommentEntity.lastModified,
                nickname = recommentEntity.user.nickname,
            )
        }
        return recomments
    }

    override fun createComment(userId: String, articleId: Long, content: String, at: LocalDateTime): Comment {
        val user = userRepository.findByUserId(userId) ?: throw CommentUserNotFoundException()
        val article = articleRepository.findByIdOrNull(articleId) ?: throw CommentArticleNotFoundException()
        val comment = commentRepository.save(
            CommentEntity(
                content = content,
                lastModified = at,
                user = user,
                article = article,
            )
        )
        return Comment(
            id = comment.id,
            content = comment.content,
            lastModified = comment.lastModified,
            nickname = comment.user.nickname,
        )
    }

    override fun createRecomment(userId: String, commentId: Long, content: String, at: LocalDateTime) : Recomment {
        val user = userRepository.findByUserId(userId) ?: throw CommentUserNotFoundException()
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw CommentNotFoundException()
        val recomment = recommentRepository.save(
            RecommentEntity(
                content = content,
                lastModified = at,
                user = user,
                comment = comment,
            )
        )
        return Recomment(
            id = recomment.id,
            content = recomment.content,
            lastModified = recomment.lastModified,
            nickname = recomment.user.nickname,
        )
    }

    override fun updateComment(id: Long, userId: String, content: String, at: LocalDateTime) : Comment {
        val comment = commentRepository.findByIdOrNull(id) ?: throw CommentNotFoundException()
        if (comment.user.userId != userId) {
            throw InvalidCommentUserException()
        }
        comment.content = content
        comment.lastModified = at
        return Comment(
            id = comment.id,
            content = comment.content,
            lastModified = comment.lastModified,
            nickname = comment.user.nickname,
            recomments = comment.recomments.map { recommentEntity ->
                Recomment(
                    id = recommentEntity.id,
                    content = recommentEntity.content,
                    lastModified = recommentEntity.lastModified,
                    nickname = recommentEntity.user.nickname,
                )
            }
        )
    }

    override fun updateRecomment(id: Long, userId: String, content: String, at: LocalDateTime) : Recomment {
        val recomment = recommentRepository.findByIdOrNull(id) ?: throw RecommentNotFoundException()
        if (recomment.user.userId != userId) {
            throw InvalidCommentUserException()
        }
        recomment.content = content
        recomment.lastModified = at
        return Recomment(
            id = recomment.id,
            content = recomment.content,
            lastModified = recomment.lastModified,
            nickname = recomment.user.nickname,
        )
    }

    override fun deleteComment(id: Long, userId: String) {
        val comment = commentRepository.findByIdOrNull(id) ?: throw CommentNotFoundException()
        if (comment.user.userId != userId) {
            throw InvalidCommentUserException()
        }
        commentRepository.delete(comment)
    }

    override fun deleteRecomment(id: Long, userId: String) {
        val recomment = recommentRepository.findByIdOrNull(id) ?: throw RecommentNotFoundException()
        if (recomment.user.userId != userId) {
            throw InvalidCommentUserException()
        }
        recommentRepository.delete(recomment)
    }
}
