package com.example.cafe.comment.service

import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.comment.repository.CommentEntity
import com.example.cafe.comment.repository.CommentRepository
import com.example.cafe.comment.repository.RecommentEntity
import com.example.cafe.comment.repository.RecommentRepository
import com.example.cafe.user.repository.UserRepository
import com.example.cafe.user.service.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CommentServiceImpl (
    private val commentRepository: CommentRepository,
    private val recommentRepository: RecommentRepository,
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
) : CommentService {
    override fun getComments(userId: Long, articleId: Long): List<Comment> {
        val article = articleRepository.findByIdOrNull(articleId) ?: throw CommentArticleNotFoundException()
        val comments = article.comments.map{ commentEntity ->
            Comment(
                id = commentEntity.id,
                content = if (commentEntity.isSecret == false) {
                    commentEntity.content // comment가 비밀이 아닌 경우 content 보여줌
                } else {
                       if (commentEntity.user.id == userId ||
                           commentEntity.article.user.id == userId) {
                           commentEntity.content // 유저가 comment 또는 article 작성자인 경우 content 보여줌
                       } else {
                           "비밀 댓글입니다." // content 안 보여줌
                       }
                },
                lastModified = commentEntity.lastModified,
                nickname = commentEntity.user.nickname,
                recomments = commentEntity.recomments.map { recommentEntity ->
                    Recomment(
                        id = recommentEntity.id,
                        content = if (recommentEntity.isSecret == false) {
                            recommentEntity.content // recomment가 비밀이 아닌 경우 content 보여줌
                        } else {
                               if (recommentEntity.user.id == userId ||
                                   commentEntity.user.id == userId ||
                                   commentEntity.article.user.id == userId) {
                                   recommentEntity.content // 유저가 recomment 또는 comment 또는 article 작성자인 경우 content 보여줌
                               } else {
                                   "비밀 대댓글입니다." // content 안 보여줌
                               }
                        },
                        lastModified = recommentEntity.lastModified,
                        nickname = recommentEntity.user.nickname,
                        isSecret = recommentEntity.isSecret,
                    )
                },
                isSecret = commentEntity.isSecret,
            )
        }
        return comments
    }

    override fun getRecomments(userId: Long, commentId: Long): List<Recomment> {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw CommentNotFoundException()
        val recomments = comment.recomments.map{ recommentEntity ->
            Recomment(
                id = recommentEntity.id,
                content = if (recommentEntity.isSecret == false) { // recomment가 비밀이 아닌 경우 content 보여줌
                    recommentEntity.content
                } else {
                    if (recommentEntity.user.id == userId ||
                        comment.user.id == userId ||
                        comment.article.user.id == userId) {
                        recommentEntity.content // 유저가 recomment 또는 comment 또는 article 작성자인 경우 content 보여줌
                    } else {
                        "비밀 대댓글입니다." // content 안 보여줌
                    }
                },
                lastModified = recommentEntity.lastModified,
                nickname = recommentEntity.user.nickname,
                isSecret = recommentEntity.isSecret,
            )
        }
        return recomments
    }

    @Transactional
    override fun createComment(userId: Long, articleId: Long, content: String, isSecret: Boolean, at: LocalDateTime): Comment {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException() }
        val article = articleRepository.findByIdOrNull(articleId) ?: throw CommentArticleNotFoundException()

        if (content=="") throw PostBadCommentContentException()

        val comment = commentRepository.save(
            CommentEntity(
                content = content,
                lastModified = at,
                user = user,
                article = article,
                isSecret = isSecret,
            )
        )
        articleRepository.incrementCommentCnt(articleId)
        userRepository.incrementCommentsCount(userId)

        return Comment(
            id = comment.id,
            content = comment.content,
            lastModified = comment.lastModified,
            nickname = comment.user.nickname,
            isSecret = comment.isSecret,
        )
    }

    @Transactional
    override fun createRecomment(userId: Long, articleId: Long, commentId: Long, content: String, isSecret: Boolean, at: LocalDateTime) : Recomment {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException() }
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw CommentNotFoundException()

        if (content=="") throw PostBadCommentContentException()

        val recomment = recommentRepository.save(
            RecommentEntity(
                content = content,
                lastModified = at,
                user = user,
                comment = comment,
                isSecret = isSecret,
            )
        )
        articleRepository.incrementCommentCnt(articleId)
        userRepository.incrementCommentsCount(userId)

        return Recomment(
            id = recomment.id,
            content = recomment.content,
            lastModified = recomment.lastModified,
            nickname = recomment.user.nickname,
            isSecret = recomment.isSecret,
        )
    }

    @Transactional
    override fun updateComment(id: Long, userId: Long, content: String, at: LocalDateTime) : Comment {
        val comment = commentRepository.findByIdOrNull(id) ?: throw CommentNotFoundException()
        if (comment.user.id != userId) {
            throw UnauthorizedCommentUserException()
        }

        if (content=="") throw PostBadCommentContentException()

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
                    isSecret = recommentEntity.isSecret,
                )
            },
            isSecret = comment.isSecret,
        )
    }

    @Transactional
    override fun updateRecomment(id: Long, userId: Long, content: String, at: LocalDateTime) : Recomment {
        val recomment = recommentRepository.findByIdOrNull(id) ?: throw RecommentNotFoundException()
        if (recomment.user.id != userId) {
            throw UnauthorizedCommentUserException()
        }

        if (content=="") throw PostBadCommentContentException()

        recomment.content = content
        recomment.lastModified = at
        return Recomment(
            id = recomment.id,
            content = recomment.content,
            lastModified = recomment.lastModified,
            nickname = recomment.user.nickname,
            isSecret = recomment.isSecret,
        )
    }

    @Transactional
    override fun deleteComment(id: Long, userId: Long, articleId: Long) {
        val comment = commentRepository.findByIdOrNull(id) ?: throw CommentNotFoundException()
        if (comment.user.id != userId) {
            throw UnauthorizedCommentUserException()
        }
        commentRepository.delete(comment)
        articleRepository.decrementCommentCnt(articleId)
        userRepository.decrementCommentsCount(userId)
    }

    @Transactional
    override fun deleteRecomment(id: Long, userId: Long, articleId: Long) {
        val recomment = recommentRepository.findByIdOrNull(id) ?: throw RecommentNotFoundException()
        if (recomment.user.id != userId) {
            throw UnauthorizedCommentUserException()
        }
        recommentRepository.delete(recomment)
        articleRepository.decrementCommentCnt(articleId)
        userRepository.decrementCommentsCount(userId)
    }
}
