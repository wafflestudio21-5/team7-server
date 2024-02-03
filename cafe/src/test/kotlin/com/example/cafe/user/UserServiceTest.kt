package com.example.cafe.user

import com.example.cafe.user.service.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userService: UserService,
) {

    @Test
    fun `유저 아이디는 5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능하다`() {
        assertThrows<SignUpBadUserIdException> {
            userService.signUp(
                username = "bad",
                password = "password1#",
                name = "username",
                email = "doo@naver.com",
                birthDate = "2000.01.01",
                phoneNumber = "01012345678",
            )
        }

        val user = assertDoesNotThrow {
            userService.signUp(
                username = "username",
                password = "password1#",
                name = "username",
                email = "doo@naver.com",
                birthDate = "2000.01.01",
                phoneNumber = "01012345678",
            )
        }

        assertThat(user.nickname).isEqualTo("username")
    }

    @Test
    fun `이미 존재하는 유저 이름으로 가입할 수 없다`() {
        assertDoesNotThrow {
            userService.signUp(
                username = "username",
                password = "password1#",
                name = "username",
                email = "doo@naver.com",
                birthDate = "2000.01.01",
                phoneNumber = "01012345678",
            )
        }

        assertThrows<SignUpUserIdConflictException> {
            userService.signUp(
                username = "username",
                password = "password1#",
                name = "username",
                email = "doo@naver.com",
                birthDate = "2000.01.01",
                phoneNumber = "01012345678",
            )
        }
    }

    @Test
    fun `로그인시 유저 이름과 비밀번호가 정확해야 한다`() {
        assertDoesNotThrow {
            userService.signUp(
                username = "username",
                password = "correct1#",
                name = "username",
                email = "doo@naver.com",
                birthDate = "2000.01.01",
                phoneNumber = "01012345678",
            )
        }

        assertThrows<SignInUserNotFoundException> {
            userService.signIn(
                username = "username-404",
                password = "spring",
            )
        }

        assertThrows<SignInInvalidPasswordException> {
            userService.signIn(
                username = "username",
                password = "wrong1#",
            )
        }

        assertDoesNotThrow {
            userService.signIn(
                username = "username",
                password = "correct1#",
            )
        }
    }
}
