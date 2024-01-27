package com.example.cafe.user.service

import java.time.LocalDate

interface UserService {
    fun signUp(username: String, password: String, name: String, email: String, birthDate: String, phoneNumber: String, at: LocalDate = LocalDate.now()): User
    fun signIn(username: String, password: String): User
    fun updateProfile(id: Long, nickname: String, introduction: String, image: String): User
    fun delete(id: Long)
    fun getUserBrief(id: Long): UserBrief
    fun authenticate(accessToken: String): User
    fun getProfile(id: Long): UserProfile
}
