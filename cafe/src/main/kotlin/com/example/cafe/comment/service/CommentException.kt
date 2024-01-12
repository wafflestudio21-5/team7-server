package com.example.cafe.comment.service

sealed class CommentException : RuntimeException()

class CommentNotFoundException : CommentException()

class RecommentNotFoundException : CommentException()

class InvalidCommentUserException : CommentException() // 댓글, 대댓글 작성자가 아닌데 수정 또는 삭제 요청
