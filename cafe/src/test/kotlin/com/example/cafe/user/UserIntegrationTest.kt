package com.example.cafe.user

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {

    @Test
    fun `회원가입시에 유저 이름 혹은 비밀번호가 정해진 규칙에 맞지 않는 경우 400 응답을 내려준다`() {
        // bad username
        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "bad",
                            "username" to "username",
                            "password" to "correct123",
                            "email" to "correct@naver.com",
                            "birthDate" to "2000.01.01",
                            "phoneNumber" to "01012345678",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(400))

        // bad password
        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "username" to "username",
                            "password" to "bad",
                            "email" to "correct@naver.com",
                            "birthDate" to "2000.01.01",
                            "phoneNumber" to "01012345678",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(400))

        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "username" to "username",
                            "password" to "password123",
                            "email" to "correct@naver.com",
                            "birthDate" to "2000.01.01",
                            "phoneNumber" to "01012345678",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))
    }

    @Test
    fun `회원가입시에 이미 해당 유저 이름이 존재하면 409 응답을 내려준다`() {
        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "username" to "username",
                            "password" to "password123",
                            "email" to "correct@naver.com",
                            "birthDate" to "2000.01.01",
                            "phoneNumber" to "01012345678",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "username" to "username",
                            "password" to "password123",
                            "email" to "correct@naver.com",
                            "birthDate" to "2000.01.01",
                            "phoneNumber" to "01012345678",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(409))
    }

    @Test
    fun `로그인 정보가 정확하지 않으면 404 응답을 내려준다`() {
        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "username" to "username",
                            "password" to "password123",
                            "email" to "correct@naver.com",
                            "birthDate" to "2000.01.01",
                            "phoneNumber" to "01012345678"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))

        // not exist username
        mvc.perform(
            post("/api/v1/signin")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid-404",
                            "password" to "password123"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(404))

        // wrong password
        mvc.perform(
            post("/api/v1/signin")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "password" to "wrong123"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(404))

        mvc.perform(
            post("/api/v1/signin")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "password" to "password123"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))
    }

    @Test
    fun `로그인 아웃 정보가 정확하지 않으면 404 응답을 내려준다`() {
        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "username" to "username",
                            "password" to "password123",
                            "email" to "correct@naver.com",
                            "birthDate" to "2000.01.01",
                            "phoneNumber" to "01012345678"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))

        // not exist username
        mvc.perform(
            post("/api/v1/signout")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid-404",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(404))

        mvc.perform(
            post("/api/v1/signout")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))
    }

    @Test
    fun `회원 정보 수정시 사용중인 닉네임으로 변경할 수 없다`() {
        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "username" to "nickname1",
                            "password" to "password123",
                            "email" to "correct@naver.com",
                            "birthDate" to "2000.01.01",
                            "phoneNumber" to "01012345678"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid2",
                            "username" to "nickname2",
                            "password" to "password123",
                            "email" to "correct@naver.com",
                            "birthDate" to "2000.01.01",
                            "phoneNumber" to "01012345678"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))

        // not exist userid
        mvc.perform(
            put("/api/v1/users/user")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid-404",
                            "nickname" to "nickname1",
                            "content" to "content",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(404))

        //duplicated nickname
        mvc.perform(
            put("/api/v1/users/user")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "nickname" to "nickname2",
                            "content" to "content",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(409))

        mvc.perform(
            put("/api/v1/users/user")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "nickname" to "nickname",
                            "content" to "content",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))
    }

    @Test
    fun `기존의 자신이 사용하던 닉네임을 사용해도 예외를 던지지 않는다`() {
        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "username" to "nickname",
                            "password" to "password123",
                            "email" to "correct@naver.com",
                            "birthDate" to "2000.01.01",
                            "phoneNumber" to "01012345678"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            put("/api/v1/users/user")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "userId" to "userid",
                            "nickname" to "nickname",
                            "content" to "content",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))
    }

    @Test
    fun `유저 요약 정보 조회`() {
        mvc.perform(
            get("/api/v1/users/user-brief?userId=doo")
        )
            .andExpect(status().`is`(200))

    }
}
