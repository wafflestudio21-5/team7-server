package com.example.cafe.user.service

import java.sql.Date
import java.time.LocalDate

interface AuthService {
    fun socialSignin(
        snsId: String,
        name: String,
        nickname: String,
        email: String,
        birthDate: Date,
        phoneNumber: String,
        at: LocalDate
    ): String
}