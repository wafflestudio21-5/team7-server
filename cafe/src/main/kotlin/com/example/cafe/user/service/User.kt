package com.example.cafe.user.service

data class User(
    val userId: String,
    val username: String,
)

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authenticated

