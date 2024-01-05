package com.example.cafe.user.service

interface UserService {
    fun signUp(userId: String, username: String, password: String, email: String, birthDate: String, phoneNumber: String): User
}
