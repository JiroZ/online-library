package com.onlinelibrary.bookservice.services.userdetailservices

import com.onlinelibrary.bookservice.respositories.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class DefaultUserDetailService(
    private val userRepository: UserRepository,
) : UserDetailsService
{
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findById(username).get()
        return User(user.email, user.password, user.libraryAuthority)
    }
}
