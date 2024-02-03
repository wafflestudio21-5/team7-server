package com.example.cafe.user.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ValidationUtil {
    fun isUsernameValid(username: String): Boolean {
        val regex = Regex("^(?!\\d+$)(?!.*([a-z0-9_-])\\1{4,})[a-z0-9_-]{5,20}$")
        return regex.matches(username)
    }

    fun isPasswordValid(password: String): Boolean {
        val escapedPassword = Regex.escape(password)
        val regex = Regex("^(?=.*[a-zA-Z])(?=.*[!@#\$%^&*()-_+=])[$escapedPassword]{8,16}$")
        return regex.matches(password)
    }

    fun isEmailValid(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
        return regex.matches(email)
    }

    fun isBirthDateValid(birthdateString: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd")
        dateFormat.isLenient = false
        return try {
            val birthdate = dateFormat.parse(birthdateString)
            birthdate != null && birthdate.before(Date())
        } catch (e: ParseException) {
            false
        }
    }

    fun isPhoneNumberValid(phoneNumber: String): Boolean {
        val regex = Regex("^010[0-9]{8}$")
        return regex.matches(phoneNumber)
    }
}