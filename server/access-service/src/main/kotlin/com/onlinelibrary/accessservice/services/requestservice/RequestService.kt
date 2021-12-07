package com.onlinelibrary.accessservice.services.requestservice

import com.onlinelibrary.accessservice.dto.book.Book
import com.onlinelibrary.accessservice.dto.user.LibraryUser
import com.onlinelibrary.accessservice.exceptions.BookException
import com.onlinelibrary.accessservice.exceptions.BookNotFoundException
import com.onlinelibrary.accessservice.exceptions.UserException
import com.onlinelibrary.accessservice.exceptions.UserNotFoundException
import com.onlinelibrary.accessservice.utils.Routes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
class RequestService(
    @Autowired
    val webClient: WebClient.Builder
) {
    @Throws(UserException::class)
    fun getUserByEmail(userName: String): LibraryUser {
        try {
            return webClient.build()
                .get()
                .uri(Routes.USER_SERVICE + "user/" + userName) //userService . getBlogUserByUsername (blogRequestMessage.userName)
                .retrieve()
                .bodyToMono<LibraryUser>()
                .block()!!
        } catch (e: Exception) {
            println(e.stackTrace)
            throw UserNotFoundException("Error Loading user through request service: $e")
        }
    }

    @Throws(BookException::class)
    fun getBookById(bookId: String) : Book {
        try {
            return webClient.build()
                .get()
                .uri(Routes.BOOK_SERVICE + "/" + bookId)
                .retrieve()
                .bodyToMono<Book>()
                .block()!!
        } catch (e: Exception) {
            throw BookNotFoundException("Error Loading book through request service: $e")
        }
    }

    val userAuthentication = SecurityContextHolder.getContext().authentication
    val isUserAuthenticated = (SecurityContextHolder.getContext().authentication != null)
}