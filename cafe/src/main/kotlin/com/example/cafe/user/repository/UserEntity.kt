package com.example.cafe.user.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.sql.Date

@Entity(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val userId: String,
    val password: String,
    val username: String,
    val email: String,
    val birthDate: Date,
    val phoneNumber: String,
)
