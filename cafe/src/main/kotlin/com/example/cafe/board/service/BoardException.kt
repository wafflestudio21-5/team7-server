package com.example.cafe.board.service

sealed class BoardException : RuntimeException()

class UserNotFoundException : BoardException()