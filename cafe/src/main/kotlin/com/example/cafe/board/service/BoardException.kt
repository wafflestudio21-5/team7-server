package com.example.cafe.board.service

sealed class BoardException : RuntimeException()

class BoardUserNotFoundException : BoardException()
class BoardNotFoundException : BoardException()
class BoardAlreadyLikedException : BoardException()
class BoardNeverLikedException : BoardException()
class IllegalSortArgumentException : BoardException()