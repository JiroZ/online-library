package com.onlinelibrary.bookservice.dto.messages

import com.onlinelibrary.bookservice.dto.book.Book
import com.onlinelibrary.bookservice.dto.user.LibraryUser
import org.springframework.web.multipart.MultipartFile

class Messages {
    data class UserAuthRequest(
        val email: String,
        val password: String
    )

    data class UserRegistrationRequest(
        val email: String,
        val userName: String,
        val password: String
    )

    data class RegistrationResponse(
        var registrationNotice: String,
        var isRegistered: Boolean,
        var user: LibraryUser?
    )

    data class UserAuthResponse(
        var user: LibraryUser?,
        var isAuthenticated: Boolean,
        var authNotice: String,
        var token: String
    )

    data class AddBookRequestMessage(
        val bookTitle: String,
        val bookDescription: String,
        val bookFile: MultipartFile
    )

    data class BookResponseMessage(
        val book: Book
    )

    data class BookFileResponseMessage(
        val book: Book,
        val bookFileByte: ByteArray
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as BookFileResponseMessage

            if (book != other.book) return false
            if (!bookFileByte.contentEquals(other.bookFileByte)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = book.hashCode()
            result = 31 * result + bookFileByte.contentHashCode()
            return result
        }
    }

    data class RevokeAccessRequestMessage(
        val bookId: String,
        val emailId: String
    )

    data class BookDeleteResponseMessage(
        val bookId: String,
        val notice: String
    )

    data class BookAddResponseMessage(
        val bookId: String,
        val notice: String
    )
}