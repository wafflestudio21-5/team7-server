package com.example.cafe.article.service

sealed class ArticleException :RuntimeException()

class BoardNotFoundException : ArticleException()

class PostBadTitleException : ArticleException()

class PostBadContentException : ArticleException()

class UserNotFoundException : ArticleException()

class ArticleNotFoundException : ArticleException()

class ArticleAlreadyLikedException: ArticleException()

class ArticleNotLikedException: ArticleException()

class UnauthorizedModifyException : ArticleException()

class RankNotFoundException : ArticleException()

class BadCategoryException : ArticleException()

class HotSortPropertyNotFoundException : ArticleException()