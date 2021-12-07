package com.onlinelibrary.userservice.services.userdetailservice

import com.onlinelibrary.userservice.exceptions.UserException
import com.onlinelibrary.userservice.exceptions.UserNotFoundException
import com.onlinelibrary.userservice.repositories.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class DefaultUserDetailService(
    private val userRepository: UserRepository,
) : UserDetailsService
{
    @Throws(UserException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        try {
            val user = userRepository.findById(email).get()
            return User(user.email, user.password, user.libraryAuthority)
        } catch (e: Exception) {
            throw UserNotFoundException("An exception occurred while finding user $e")
        }
    }
}
