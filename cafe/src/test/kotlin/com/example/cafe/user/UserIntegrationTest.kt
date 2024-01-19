package com.example.cafe.user

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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
}
