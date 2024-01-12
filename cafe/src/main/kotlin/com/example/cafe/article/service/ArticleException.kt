package com.example.cafe.article.service

sealed class ArticleException :RuntimeException()

class BoardNotFoundException : ArticleException()

class PostBadTitleException : ArticleException()

class PostBadContentException : ArticleException()

class UserNotFoundException : ArticleException()
