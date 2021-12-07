package com.onlinelibrary.accessservice.services.userdetailservice

import com.onlinelibrary.accessservice.repositories.UserRepository
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
