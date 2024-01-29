package com.example.cafe.user.service

import com.example.cafe.security.SecurityService
import java.time.LocalDate

data class User(
    val id: Long,
    val nickname: String,
    val registerDate: LocalDate,
    val email: String,
    val rank: String,
    val visitCount: Long,
    val articlesCount: Long,
    val commentsCount: Long,
) {

    fun getAccessToken(): String {
        return SecurityService().createSecretKey(id.toString())
    }

    enum class Rank(val s: String) {
        USER("씨앗"),
        ADMIN("관리자")
    }

}



@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authenticated

