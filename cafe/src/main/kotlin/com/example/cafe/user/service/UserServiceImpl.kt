package com.example.cafe.user.service

import com.example.cafe.user.repository.UserEntity
import com.example.cafe.user.repository.UserRepository
import org.springframework.stereotype.Service
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
        phoneNumber: String
    ): User {
        val entity = userRepository.save(
            UserEntity(
                userId = userId,
                username = username,
                password = password,
                email = email,
                birthDate = birthDate,
                phoneNumber = phoneNumber,
            )
        )
        return User(entity)
    }

    fun User(entity: UserEntity) = User(
        userId = entity.userId,
        username = entity.username
    )

}
