package com.example.cafe.user.service

import com.example.cafe._web.exception.AuthenticateException
import com.example.cafe.security.SecurityService
import com.example.cafe.cafe.repository.CafeRepository
import com.example.cafe.user.repository.UserEntity
import com.example.cafe.user.repository.UserRepository
import com.example.cafe.user.util.ValidationUtil
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDate

@Service
class UserServiceImpl (
    private val userRepository: UserRepository,
    private val securityService: SecurityService,
    private val cafeRepository: CafeRepository,
) : UserService {
    @Transactional
    override fun signUp(
        username: String,
        password: String,
        name: String,
        email: String,
        birthDate: String,
        phoneNumber: String,
        at: LocalDate
    ): User {
        validate(username, password, email, birthDate, phoneNumber)
        val entity = userRepository.save(
            UserEntity(
                username = username,
                password = password,
                name = name,
                email = email,
                birthDate = toSqlDate(birthDate),
                phoneNumber = phoneNumber,
                registerDate = at
            )
        )

        cafeRepository.incrementMemberCnt(1)
        return User(entity)
    }

    override fun signIn(username: String, password: String): User {
        val entity = userRepository.findByUsername(username) ?: throw SignInUserNotFoundException()

        if (entity.password != password) {
            throw SignInInvalidPasswordException()
        }

        return User(entity)
    }

    @Transactional
    override fun updateProfile(id: Long, nickname: String, introduction: String, image: String): User {
        val entity: UserEntity = userRepository.findById(id).orElseThrow { UserNotFoundException() }
        val currentNickname = entity.nickname

        if (nickname != currentNickname && userRepository.findByNickname(nickname) != null) throw NicknameConflictException()
        entity.nickname = nickname
        entity.introduction = introduction
        entity.image = image

        return User(entity)
    }

    @Transactional
    override fun delete(id: Long) {
        val entity: UserEntity = userRepository.findById(id).orElseThrow { UserNotFoundException() }

        userRepository.delete(entity)
    }

    override fun getUserBrief(id: Long): UserBrief {
        val entity: UserEntity = userRepository.findById(id).orElseThrow { UserNotFoundException() }

        return UserBrief(
            nickname = entity.nickname,
            rank = entity.rank,
            visit_count = entity.visitCount,
            my_article_count = entity.articlesCount,
            my_comment_count = entity.commentsCount,
            register_date = entity.registerDate
        )
    }

    override fun authenticate(accessToken: String): User {
        val id = securityService.getSubject(accessToken)!!.toLong()
        val entity = userRepository.findById(id).orElseThrow { AuthenticateException() }

        return User(entity)
    }

    private fun validate(username: String, password: String, email: String, birthDate: String, phoneNumber: String) {
        val validationUtil = ValidationUtil()

        if (!validationUtil.isUsernameValid(username)) {
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

        if (userRepository.findByUsername(username) != null) {
            throw SignUpUserIdConflictException()
        }
    }

    private fun toSqlDate(birthDate: String): Date {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd")
        val utilDate = dateFormat.parse(birthDate)
        return Date(utilDate.time)
    }

    fun User(entity: UserEntity) = User(
        id = entity.id,
        nickname = entity.nickname,
        registerDate = entity.registerDate,
        email = entity.email,
        rank = entity.rank,
        visitCount = entity.visitCount,
        articlesCount = entity.articlesCount,
        commentsCount = entity.commentsCount
    )
}
