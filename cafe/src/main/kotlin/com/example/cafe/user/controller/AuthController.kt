package com.example.cafe.user.controller

import com.example.cafe.security.SecurityService
import com.example.cafe.user.repository.UserRepository
import com.example.cafe.user.service.AuthService
import com.example.cafe.user.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.LocalDate
import java.sql.Date
import java.util.Calendar

@RestController
class AuthController(
    private val userService: UserService,
    private val authService: AuthService,
    private val webClient: WebClient,
    private val userRepository: UserRepository,
    private val securityService: SecurityService,
) {
    private val clientId = "OemkbWdkEHFr93oA3sxR"
    private val clientSecret = "O46GLDeEKM"
    private val profileUrl = "https://openapi.naver.com/v1/nid/me"

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): SignInResponse {
        val user = userService.signIn(
            username = request.username,
            password = request.password
        )

        return SignInResponse(user.getAccessToken())
    }

    @GetMapping("/api/v1/auth/socialSignin/naver")
    fun socialSignIn(
        @RequestParam code: String
    ): Mono<SignInResponse> {
        val accessTokenUrl = "https://nid.naver.com/oauth2.0/token" +
                "?client_id=$clientId&client_secret=$clientSecret&code=$code&response_type=code&grant_type=authorization_code"

        val objectMapper = ObjectMapper()

        // get access token
        val accessTokenMono = webClient.post()
            .uri(accessTokenUrl)
            .retrieve()
            .bodyToMono(String::class.java)
            .flatMap { response ->
                // Parse the JSON response to extract the access token
                val jsonResponse = objectMapper.readValue(response, NaverAccessTokenResponse::class.java)
                val accessToken = jsonResponse.access_token

                if (accessToken == null) {
                    Mono.error { RuntimeException("Access token not found") }
                } else {
                    Mono.just(accessToken)
                }
            }

        // get user profile
        return accessTokenMono.flatMap { accessToken ->
            webClient.get()
                .uri(profileUrl)
                .header("Authorization", "Bearer $accessToken") // Set access token as Authorization header
                .retrieve()
                .bodyToMono(NaverProfileResponse::class.java)
                .publishOn(Schedulers.boundedElastic())
                .flatMap { profileResponse ->
                    val profile = profileResponse.response

                    val accessToken = authService.socialSignin(
                        snsId = profile.id,
                        nickname = profile.nickname,
                        name = profile.name,
                        email = profile.email,
                        birthDate = toDate(profile.birthyear, profile.birthday),
                        phoneNumber = profile.mobile.replace("-", ""),
                        at = LocalDate.now()
                    )

                    Mono.just(SignInResponse(accessToken))
                }
                .switchIfEmpty(Mono.error { RuntimeException("Failed to fetch user profile") })
        }
    }

    private fun toDate(year: String, monthAndDay: String): Date {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(
            year.toLong().toInt(),
            monthAndDay.split("-")[0].toInt(),
            monthAndDay.split("-")[0].toInt()
        )
        return Date(calendar.timeInMillis)
    }

    data class SignInResponse(
        val accessToken: String,
    )

    data class NaverAccessTokenResponse(
        val access_token: String?,
        val refresh_token: String?,
        val token_type: String?,
        val expires_in: String?
    ) {
        constructor() : this(null, null, null, null)
    }

    data class NaverAccessTokenErrorResponse(
        val error: String,
        val errorDescription: String,
    )

    data class NaverProfileResponse(
        val resultcode: String,
        val message: String,
        val response: NaverUser
    )

    data class NaverUser(
        val id: String,
        val nickname: String,
        val email: String,
        val mobile: String,
        val mobile_e164: String,
        val name: String,
        val birthday: String,
        val birthyear: String
    )

}