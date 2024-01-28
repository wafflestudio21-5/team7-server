package com.example.cafe.user.service

import com.example.cafe._web.exception.AuthenticateException
import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.article.service.ArticleBrief
import com.example.cafe.board.service.Board
import com.example.cafe.security.SecurityService
import com.example.cafe.cafe.repository.CafeRepository
import com.example.cafe.user.repository.UserEntity
import com.example.cafe.user.repository.UserRepository
import com.example.cafe.user.util.RandomNicknameGenerator
import com.example.cafe.user.util.ValidationUtil
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDate

@Service
class UserServiceImpl (
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
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
                nickname = generateUniqueNickname(),
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
            register_date = entity.registerDate,
            image = entity.image
        )
    }

    override fun authenticate(accessToken: String): User {
        val id = securityService.getSubject(accessToken)!!.toLong()
        val entity = userRepository.findById(id).orElseThrow { AuthenticateException() }

        return User(entity)
    }

    override fun getProfile(id: Long): UserProfile {
        val entity: UserEntity = userRepository.findById(id).orElseThrow { UserNotFoundException() }

        return UserProfile(nickname = entity.nickname, introduction = entity.introduction, image = entity.image)
    }

    override fun getUserInfo(nickname: String): UserInfo {
        val entity: UserEntity = userRepository.findByNickname(nickname)?: throw UserNotFoundException()

        val pageable = PageRequest.of(0, 15)
        val articles = articleRepository.findFirst15ByUserId(userId = entity.id, pageable = pageable)
        return UserInfo(
            nickname = entity.nickname,
            rank = entity.rank,
            introduction = entity.introduction,
            visit_count = entity.visitCount,
            my_article_count = entity.articlesCount,
            my_comment_count = entity.commentsCount,
            register_date = entity.registerDate,
            image = entity.image,
            articles = articles!!.stream()
                .map { ArticleBrief(
                    id = it.id,
                    title = it.title,
                    createdAt = it.createdAt,
                    viewCount = it.viewCnt,
                    likeCount = it.likeCnt,
                    commentCount = it.commentCnt,
                    author = User(it.user),
                    board = Board(id = it.board.id, name = it.board.name),
                    isNotification = it.isNotification) }
                .toList()
            )
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

    private fun generateUniqueNickname(): String {
        var generatedNickname: String
        do {
            generatedNickname = RandomNicknameGenerator().generate()
        } while (userRepository.existsByNickname(generatedNickname))
        return generatedNickname
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
