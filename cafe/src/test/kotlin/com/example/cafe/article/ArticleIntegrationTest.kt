package com.example.cafe.article

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArticleIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
){
    @Test
    @Transactional
    fun `게시물 작성`() {
        mvc.perform(
            post("/api/v1/articles/post")
                .header("Authorization","Bearer hong")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "title" to "샘플 게시물 제목",
                            "content" to "샘플 게시물 내용",
                            "boardId" to 1L,
                            "allowComments" to true,
                            "isNotification" to false
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("\$.title").value("샘플 게시물 제목"),
                )
    }

    @Test
    @Transactional
    fun `게시물 수정`() {
        mvc.perform(
            put("/api/v1/articles/1/modify")
                .header("Authorization","Bearer hong")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "title" to "샘플 게시물 제목",
                            "content" to "샘플 게시물 내용",
                            "boardId" to 1L,
                            "allowComments" to true,
                            "isNotification" to false
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("\$.id").value("1"),
                jsonPath("\$.title").value("샘플 게시물 제목"),
            )
    }

    @Test
    @Transactional
    fun `게시물 삭제`() {
        mvc.perform(
            post("/api/v1/articles/post")
                .header("Authorization","Bearer hong")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "title" to "샘플 게시물 제목",
                            "content" to "샘플 게시물 내용",
                            "boardId" to 1L, // 예시로 1번 보드를 사용
                            "allowComments" to true,
                            "isNotification" to false
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)

        mvc.perform(
            delete("/api/v1/articles/1")
                .header("Authorization","Bearer doo")
        ).andExpect(status().`is`(401))

        mvc.perform(
            delete("/api/v1/articles/1")
                .header("Authorization","Bearer hong")
        ).andExpect(status().isOk)

    }

    @Test
    @Transactional
    fun `게시물 조회`() {
        mvc.perform(
            get("/api/v1/articles/1")
                .header("Authorization","Bearer hong")
        )
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("\$.article.id").value("1"),
                //jsonPath("\$.article.viewCount").value("1"),
                jsonPath("\$.isLiked").value("false")
            )
    }

    @Test
    @Transactional
    fun `게시물 좋아요`() {
        mvc.perform(
            post("/api/v1/articles/404404404/like")
                .header("Authorization","Bearer hong")
        ).andExpect(status().`is`(404))

        mvc.perform(
            post("/api/v1/articles/1/like")
                .header("Authorization","Bearer hong")
        ).andExpect(status().isOk)

        mvc.perform(
            post("/api/v1/articles/1/like")
                .header("Authorization","Bearer hong")
        ).andExpect(status().`is`(400))
    }

    @Test
    fun `게시물 좋아요 취소`() {
        mvc.perform(
            delete("/api/v1/articles/1/like")
                .header("Authorization","Bearer hong")
        ).andExpect(status().`is`(400))

        mvc.perform(
            post("/api/v1/articles/1/like")
                .header("Authorization","Bearer hong")
        ).andExpect(status().isOk)

        mvc.perform(
            delete("/api/v1/articles/1/like")
                .header("Authorization","Bearer hong")
        ).andExpect(status().isOk)
    }

    @Test
    @Transactional
    fun `인기 게시물 조회`() {
        mvc.perform(
            delete("/api/v1/articles/1")
                .header("Authorization","Bearer hong")
        ).andExpect(status().isOk)

        mvc.perform(
            post("/api/v1/articles/post")
                .header("Authorization","Bearer hong")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "title" to "샘플 게시물 제목 1",
                            "content" to "샘플 게시물 내용 1",
                            "boardId" to 1L,
                            "allowComments" to true,
                            "isNotification" to false
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)

        mvc.perform(
            post("/api/v1/articles/post")
                .header("Authorization","Bearer hong")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "title" to "샘플 게시물 제목 2",
                            "content" to "샘플 게시물 내용 2",
                            "boardId" to 1L,
                            "allowComments" to true,
                            "isNotification" to false
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)

        mvc.perform(
            post("/api/v1/articles/3/like")
                .header("Authorization","Bearer hong")
        ).andExpect(status().isOk)
        mvc.perform(
            get("/api/v1/articles/3")
        )
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("\$.article.viewCount").value("1"),
                jsonPath("\$.article.likeCount").value("1"),
            )

        mvc.perform(
            get("/api/v1/articles/hot")
                .param("sortBy","VIEW")
        )
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.articleBrief").isArray,
                jsonPath("\$.articleBrief[0].title").value("샘플 게시물 제목 2"),
                jsonPath("\$.articleBrief[1].title").value("샘플 게시물 제목 1"),

            )
    }

    @Test
    @Transactional
    fun `게시물 전체 조회`() {
        mvc.perform(
            post("/api/v1/articles/post")
                .header("Authorization", "Bearer hong")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "title" to "샘플 게시물 제목 1",
                            "content" to "샘플 게시물 내용 1",
                            "boardId" to 1L,
                            "allowComments" to true,
                            "isNotification" to false
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)

        mvc.perform(
            post("/api/v1/articles/post")
                .header("Authorization", "Bearer hong")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "title" to "샘플 게시물 제목 2",
                            "content" to "샘플 게시물 내용 2",
                            "boardId" to 1L,
                            "allowComments" to true,
                            "isNotification" to false
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)

        println(mvc.perform(
            get("/api/v1/articles/hot")
                .param("sortBy","VIEW")
        ).toString())

        mvc.perform(
            get("/api/v1/articles")
        )
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.articleBrief.length()").value(0),
            )

        mvc.perform(
            get("/api/v1/articles")
                .header("Authorization", "Bearer hong")
        )
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.articleBrief.length()").value(3),
            )

    }
}