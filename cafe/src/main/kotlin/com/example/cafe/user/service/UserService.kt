package com.example.cafe.user.service

import java.time.LocalDateTime

interface UserService {
    fun signUp(userId: String, username: String, password: String, email: String, birthDate: String, phoneNumber: String, at: LocalDateTime = LocalDateTime.now()): User
}
