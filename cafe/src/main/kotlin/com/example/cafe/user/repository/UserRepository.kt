package com.example.cafe.user.repository

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
    fun findByNickname(nickname: String): UserEntity?
    fun findBySnsId(id: String): UserEntity?
    fun existsByNickname(nickname: String): Boolean
}
