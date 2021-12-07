package com.bloggie.userservice.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

class JwtUtil {
    companion object {
        private const val SECRET_KEY = "mySecretKey"

        fun extractUsername(token: String?): String {
            return extractClaim(token) { obj: Claims -> obj.subject }
        }

        private fun extractExpiration(token: String?): Date {
            return extractClaim(token) { obj: Claims -> obj.expiration }
        }

        private fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
            val claims = extractAllClaims(token)
            return claimsResolver.apply(claims)
        }

        fun extractAllClaims(token: String?): Claims {
            return Jwts.parser().setSigningKey(SECRET_KEY.toByteArray()).parseClaimsJws(token).body
        }

        private fun isTokenExpired(token: String?): Boolean {
            return extractExpiration(token).before(Date())
        }

        fun generateToken(userDetails: UserDetails): String {
            return createToken(userDetails, userDetails.username)
        }

        private fun createToken(userDetails: UserDetails, subject: String): String {
            return Jwts.builder()
                .claim(
                    "authorities",
                    userDetails.authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
                )
                .setSubject(subject)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(
                    SignatureAlgorithm.HS512,
                    SECRET_KEY.toByteArray()
                )
                .compact()
        }

        fun validateToken(token: String?, userDetails: UserDetails): Boolean {
            val username = extractUsername(token)
            return username == userDetails.username && !isTokenExpired(token)
        }
    }
}