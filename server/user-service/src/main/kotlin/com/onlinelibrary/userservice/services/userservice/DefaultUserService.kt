package com.onlinelibrary.userservice.services.userservice

import com.bloggie.userservice.utils.JwtUtil
import com.onlinelibrary.userservice.authority.Authorities
import com.onlinelibrary.userservice.authority.LibraryUserAuthority
import com.onlinelibrary.userservice.dto.messages.Messages
import com.onlinelibrary.userservice.dto.user.LibraryUser
import com.onlinelibrary.userservice.exceptions.*
import com.onlinelibrary.userservice.repositories.UserRepository
import com.onlinelibrary.userservice.services.userdetailservice.DefaultUserDetailService
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.authentication.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefaultUserService(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val userDetailService: DefaultUserDetailService,
) : UserService {

//    @Throws(UserException::class)
//    fun getLibraryUserByUsername(username: String): LibraryUser {
//        try {
//            return userRepository.findById(username).get()
//        } catch (e: IllegalArgumentException) {
//            throw UserNotFoundException("LibraryUser Not Found with username: $username")
//        }
//    }

    @Throws(UserException::class)
    override fun authUser(userAuthRequest: Messages.UserAuthRequest): Messages.UserAuthResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    userAuthRequest.email,
                    userAuthRequest.password
                )
            )

            val user = getUserByEmail(userAuthRequest.email)

            val userDetails = userDetailService.loadUserByUsername(user.email)
            val userJwtToken = JwtUtil.generateToken(userDetails)

            return Messages.UserAuthResponse(user, true, "Login Successful", userJwtToken)

        } catch (e: Exception) {
            when (e) {
                is BadCredentialsException -> {
                    e.printStackTrace()
                    throw WrongPasswordOrUserNameException("Wrong username/password")
                }
                is LockedException -> {
                    e.printStackTrace()
                    throw AccountLockedException("Your Account is Locked")
                }
                is DisabledException -> {
                    e.printStackTrace()
                    throw AccountDisabledException("Your Account is disabled")
                }
                is InternalAuthenticationServiceException -> {
                    e.printStackTrace()
                    throw AccountDoesntExistException("User Account does not exists")
                }
                else -> throw AuthenticationException("Error while authentication $e")
            }
        }
    }

    @Throws(UserException::class)
    override fun registerUser(userRegistrationRequest: Messages.UserRegistrationRequest): Messages.RegistrationResponse {
        try {
            var userFound = false
            val users: List<LibraryUser> = getAllUsers()
            val registrationMessage = Messages.RegistrationResponse("", false, null)

            for (dbUser in users) {
                if (dbUser.email == userRegistrationRequest.email) {
                    throw UserAlreadyExistsException("E-mail user ${userRegistrationRequest.email} already exists")
                }
            }

            if (!userFound) {
                val user = LibraryUser(
                    userRegistrationRequest.email,
                    BCryptPasswordEncoder().encode(userRegistrationRequest.password),
                    userRegistrationRequest.userName,
                    HashSet(Collections.singletonList(LibraryUserAuthority(Authorities.ROLE_USER.toString())))
                )

                userRepository.insert(user)
                registrationMessage.user = user
                registrationMessage.registrationNotice = "User Registered Successfully, You may login now."
                registrationMessage.isRegistered = true
            }
            return registrationMessage
        } catch (e: Exception) {
            when (e) {
                is DuplicateKeyException -> {
                    throw UserAlreadyExistsException("User with ${userRegistrationRequest.email} Already Exists")
                }
                else -> throw e
            }
        }
    }

    fun getAllUsers() : List<LibraryUser> {
        return userRepository.findAll()
    }

    @Throws(UserException::class)
    fun getUserByEmail(email: String): LibraryUser {
        try {
            return userRepository.findById(email).get()
        } catch (e: Exception) {
            when (e) {
                is NoSuchElementException, is IllegalArgumentException -> {
                    e.printStackTrace()
                    throw UserNameNotFoundException("E-mail: $email not found")
                }
                else -> throw e
            }
        }
    }

//    @Throws(UserException::class, BlogException::class)
//    fun deleteUser(userName: String) {
//        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails
//        val authorities = principal.authorities as Set<*>
//        try {
//            if (authorities.contains(Authorities.ROLE_ADMIN.toString())) {
//                val user = getUserByUsername(userName)
//                val userBlogs = user.blogs
//
//                userRepository.delete(user)
//
//                for (userBlog in userBlogs) {
//                    blogRepository.delete(userBlog)
//                    blogIndexRepository.deleteById(userBlog.id)
//                }
//            }
//        } catch (e: IllegalArgumentException) {
//            throw UserException("There was a problem with deleting your account. Error code: ${e.printStackTrace()}")
//        }
//    }

    val isUserAuthenticated = (SecurityContextHolder.getContext().authentication != null)
    val userAuthentication = SecurityContextHolder.getContext().authentication
}