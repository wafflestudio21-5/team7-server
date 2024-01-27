package com.example.cafe.comment.service

sealed class CommentException : RuntimeException()

class CommentNotFoundException : CommentException()

class RecommentNotFoundException : CommentException()

class UnauthorizedCommentUserException : CommentException() // 댓글, 대댓글 작성자가 아닌데 수정 또는 삭제 요청

class CommentUserNotFoundException : CommentException()

class CommentArticleNotFoundException : CommentException()

class PostBadCommentContentException : CommentException()
