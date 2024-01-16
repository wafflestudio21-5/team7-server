package com.example.cafe.user.service

import com.example.cafe.user.repository.UserEntity
import com.example.cafe.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDateTime

@Service
class UserServiceImpl (
    private val userRepository: UserRepository,
) : UserService {
    override fun signUp(
        userId: String,
        username: String,
        password: String,
        email: String,
        birthDate: String,
        phoneNumber: String,
        at: LocalDateTime
    ): User {
        val entity = userRepository.save(
            UserEntity(
                userId = userId,
                username = username,
                password = password,
                email = email,
                birthDate = toSqlDate(birthDate),
                phoneNumber = phoneNumber,
                registerDate = at
            )
        )
        return User(entity)
    }

    fun toSqlDate(birthDate: String): Date {
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val utilDate = dateFormat.parse(birthDate)
        return Date(utilDate.time)
    }

    fun User(entity: UserEntity) = User(
        userId = entity.userId,
        username = entity.username
    )

}
