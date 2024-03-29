package com.example.cafe.user.service

import com.example.cafe.security.SecurityService
import com.example.cafe.user.repository.UserEntity
import com.example.cafe.user.repository.UserRepository
import com.example.cafe.user.util.RandomNicknameGenerator
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.sql.Date
import java.time.LocalDate
@Service
class AuthServiceImpl (
    private val userRepository: UserRepository,
    private val securityService: SecurityService,
): AuthService {
    @Transactional
    override fun socialSignin(
        snsId: String,
        name: String,
        email: String,
        birthDate: Date,
        phoneNumber: String,
        at: LocalDate
    ): String {
        val existingUser = userRepository.findBySnsId(snsId)
        var secretKey: String

        if (existingUser != null) {
            userRepository.incrementVisitCnt(existingUser.id)
            // User already exists, return the user's ID
            secretKey = toSecretKey(existingUser.id.toString())
        } else {
            // User does not exist, save the profile in the repository
            val newUser = UserEntity(
                nickname = generateUniqueNickname(),
                snsId = snsId,
                name = name,
                email = email,
                birthDate = birthDate,
                phoneNumber = phoneNumber,
                registerDate = at,
            )
            userRepository.incrementVisitCnt(newUser.id)

            secretKey = toSecretKey(userRepository.save(newUser).id.toString())
        }

        return secretKey
    }

    private fun toSecretKey(userId: String): String {
        return securityService.createSecretKey(userId)
    }

    private fun generateUniqueNickname(): String {
        var generatedNickname: String
        do {
            generatedNickname = RandomNicknameGenerator().generate()
        } while (userRepository.existsByNickname(generatedNickname))
        return generatedNickname
    }
}