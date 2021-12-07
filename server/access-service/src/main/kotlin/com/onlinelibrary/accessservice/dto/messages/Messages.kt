package com.onlinelibrary.accessservice.dto.messages

import com.onlinelibrary.accessservice.dto.book.Book
import com.onlinelibrary.accessservice.dto.user.LibraryUser
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
        val bookFile: MultipartFile
    )

    data class BookResponseMessage (
        val book: Book,
        val bookFileByte : ByteArray
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as BookResponseMessage

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

    data class BookDeleteResponseMessage(
        val bookId: String,
        val notice: String
    )

    data class BookAddResponseMessage(
        val bookId: String,
        val notice: String
    )

    data class GiveAccessRequestMessage(
        val bookId: String,
        val emailId: String
    )

    data class RevokeAccessRequestMessage(
        val bookId: String,
        val emailId: String
    )

    data class RequestBookAccessMessage (
        val bookId: String,
        val emailId: String
    )

    data  class ApproveAccessRequestMessage(
        val requestId: String,
        val bookId: String,
        val emailId: String
    )

    data class RejectAccessRequestMessage(
        val requestId: String,
        val bookId: String,
        val emailId: String
    )

    data class CSVRequestMessage(
        val csvFile: MultipartFile
    )
}