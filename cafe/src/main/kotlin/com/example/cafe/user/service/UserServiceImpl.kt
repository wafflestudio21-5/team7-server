package com.example.cafe.user.service

import com.example.cafe.user.repository.UserEntity
import com.example.cafe.user.repository.UserRepository
import com.example.cafe.user.util.ValidationUtil
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
        validate(userId, password, email, birthDate, phoneNumber)
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

    private fun validate(userId: String, password: String, email: String, birthDate: String, phoneNumber: String) {
        val validationUtil = ValidationUtil()

        if (!validationUtil.isUserIdValid(userId)) {
            throw SignUpBadUserIdException()
        }

        if (!validationUtil.isPasswordValid(password)) {
            throw SignUpBadPasswordException()
        }

        if (!validationUtil.isEmailValid(email)) {
            throw SignUpBadEmailException()
        }

        if (!validationUtil.isBirthDateValid(birthDate)) {
            throw SignUpBadBirthDateException()
        }

        if (!validationUtil.isPhoneNumberValid(phoneNumber)) {
            throw SignUpBadPhoneNumberException()
        }

        if (userRepository.findByUserId(userId) != null) {
            throw SignUpUserIdConflictException()
        }
    }

    private fun toSqlDate(birthDate: String): Date {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd")
        val utilDate = dateFormat.parse(birthDate)
        return Date(utilDate.time)
    }

    fun User(entity: UserEntity) = User(
        userId = entity.userId,
        username = entity.username
    )

}
