package com.onlinelibrary.bookservice.services.requestservice

import com.onlinelibrary.bookservice.dto.book.Book
import com.onlinelibrary.bookservice.dto.book.BookAccess
import com.onlinelibrary.bookservice.dto.book.IssuedBook
import com.onlinelibrary.bookservice.dto.messages.Messages
import com.onlinelibrary.bookservice.dto.messages.Messages.*
import com.onlinelibrary.bookservice.dto.user.LibraryUser
import com.onlinelibrary.bookservice.dto.user.ServerUser
import com.onlinelibrary.bookservice.exceptions.*
import com.onlinelibrary.bookservice.utils.Routes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
class RequestService(
    @Autowired
    val webClient: WebClient.Builder,
    val serverUser: ServerUser
) {
    @Throws(UserException::class)
    fun getUserByEmail(userName: String): LibraryUser {
        try {
            return webClient.build()
                .get()
                .uri(Routes.USER_SERVICE + "user/" + userName) //userService . getBlogUserByUsername (blogRequestMessage.userName)
                .header("Authorization", serverUser.authHeader)
                .retrieve()
                .bodyToMono<LibraryUser>()
                .block()!!
        } catch (e: Exception) {
            println(e.stackTrace)
            throw UserNotFoundException("Error Loading user through request service: $e")
        }
    }

    @Throws(BookException::class)
    fun getBookById(bookId: String): Book {
        try {
            return webClient.build()
                .get()
                .uri(Routes.BOOK_SERVICE + "/" + bookId)
                .header("Authorization", serverUser.authHeader)
                .retrieve()
                .bodyToMono<Book>()
                .block()!!
        } catch (e: Exception) {
            throw BookNotFoundException("Error Loading book through request service: $e")
        }
    }

    fun getIssuedBooksByEmail(email: String): MutableList<IssuedBook> {
        try {
            return webClient.build()
                .get()
                .uri(Routes.ACCESS_SERVICE + "/" + email)
                .header("Authorization", serverUser.authHeader)
                .retrieve()
                .bodyToMono<MutableList<IssuedBook>>()
                .block()!!
        } catch (e: Exception) {
            throw BookNotFoundException("Error Loading issued books through request service: $e")
        }
    }

    fun revokeAccessRequest(message: RevokeAccessRequestMessage): String {
        try {
            return webClient.build()
                .post()
                .uri(Routes.ACCESS_SERVICE + "/revoke")
                .header("Authorization", serverUser.authHeader)
                .body(Mono.just(message), RevokeAccessRequestMessage::class.java)
                .retrieve()
                .bodyToMono<String>()
                .block()!!
        } catch (e: Exception) {
            throw UnableToRevokeAccessException("An exception occurred while revoking access")
        }
    }

    val userAuthentication = SecurityContextHolder.getContext().authentication
    val isUserAuthenticated = (SecurityContextHolder.getContext().authentication != null)
}