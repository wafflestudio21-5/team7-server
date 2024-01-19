package com.example.cafe.user.repository

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUserId(userId: String): UserEntity?
    fun findByNickname(nickname: String): UserEntity?
}
