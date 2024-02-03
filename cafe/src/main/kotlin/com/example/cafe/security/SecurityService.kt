package com.example.cafe.security

import com.example.cafe.user.service.ExpiredTokenException
import com.example.cafe.user.service.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*


@Service
class SecurityService {
    private final val secretKey = "82600EBC53E3213FEB67AA99B9F001177D548CA60291B961505DA3608D306982"
    private final val expirationDate = Date(System.currentTimeMillis() + 3600000)
    fun createSecretKey(subject: String): String {
        val now = Date()
        val keyBytes = Decoders.BASE64.decode(secretKey)
        val key: Key = Keys.hmacShaKeyFor(keyBytes)

        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key)
            .compact()
    }

    fun getSubject(jwtToken: String): String? {
        try {
            val claims: Claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken).body
            val expirationDate: Date = claims.expiration

            if (Date().before(expirationDate)) {
                return claims.subject
            }
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException()
        } catch (e: Exception) {
            throw InvalidTokenException()
        }
        return null
    }
}