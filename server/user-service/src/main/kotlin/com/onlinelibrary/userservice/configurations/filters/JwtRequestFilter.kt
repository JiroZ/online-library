package com.onlinelibrary.userservice.configurations.filters

import com.bloggie.userservice.utils.JwtUtil
import com.onlinelibrary.userservice.dto.user.ServerUser
import com.onlinelibrary.userservice.services.userdetailservice.DefaultUserDetailService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
    private val defaultUserDetailService: DefaultUserDetailService,
    private val serverUser: ServerUser
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val jwt = authorizationHeader.substring(7)

            val userName = JwtUtil.extractUsername(jwt)
            val claims = JwtUtil.extractAllClaims(jwt)

            if (claims["authorities"] != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = defaultUserDetailService.loadUserByUsername(userName)
                serverUser.email = userName
                serverUser.authHeader = authorizationHeader

                if(userDetails.authorities.toString().contains("ROLE_ADMIN")) {
                    serverUser.isAdmin = true
                }

                if (JwtUtil.validateToken(jwt, userDetails)) {
                    val userNamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities
                    )
                    userNamePasswordAuthenticationToken
                        .details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = userNamePasswordAuthenticationToken
                    response.addHeader("Authorization", authorizationHeader)
                }
            }
        }
        chain.doFilter(request, response)
    }
}