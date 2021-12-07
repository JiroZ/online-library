package com.onlinelibrary.accessservice.services.accessservice

import com.google.gson.GsonBuilder
import com.onlinelibrary.accessservice.dto.access.BookAccessRequest
import com.onlinelibrary.accessservice.dto.book.BookAccess
import com.onlinelibrary.accessservice.dto.book.IssuedBook
import com.onlinelibrary.accessservice.dto.log.LogActions
import com.onlinelibrary.accessservice.dto.log.ServerLog
import com.onlinelibrary.accessservice.dto.messages.Messages.*
import com.onlinelibrary.accessservice.dto.user.ServerUser
import com.onlinelibrary.accessservice.exceptions.*
import com.onlinelibrary.accessservice.repositories.AccessRequestRepository
import com.onlinelibrary.accessservice.repositories.BookAccessRepository
import com.onlinelibrary.accessservice.repositories.ServerLogRepository
import com.onlinelibrary.accessservice.services.requestservice.RequestService
import com.univocity.parsers.csv.CsvParser
import com.univocity.parsers.csv.CsvParserSettings
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStreamReader
import java.util.*


@Service
class DefaultAccessService(
    val requestService: RequestService,
    val accessRequestRepository: AccessRequestRepository,
    val bookAccessRepository: BookAccessRepository,
    val serverLogRepository: ServerLogRepository,
    val serverUser: ServerUser
) : AccessService {

    @Throws(AccessException::class)
    override fun revokeAccess(message: RevokeAccessRequestMessage): String {
        try {
            val user = requestService.getUserByEmail(message.emailId)
            val issuedBook = requestService.getBookById(message.bookId)

            val accesses = bookAccessRepository.findById(user.email).get()

            accesses.issuedBooks.forEach { book ->
                run {
                    if (book.bookId == issuedBook.id) {
                        accesses.issuedBooks.remove(book)
                    }
                }
            }
            bookAccessRepository.save(accesses)

            serverLogRepository.insert(
                ServerLog(
                    ObjectId().toString(),
                    LogActions.REVOKE_ACCESS.toString(),
                    "Access revoked for email: ${user.email}, from book ${issuedBook.id} : ${issuedBook.date}",
                    Date(System.currentTimeMillis())
                )
            )

            return "Access revoke for ${message.emailId} of book ${message.bookId} completed successfully"
        } catch (e: Exception) {
            throw RevokeAccessException("An exception occurred while revoking the book access: $e")
        }
    }

    @Throws(AccessException::class)
    fun issueAccess(message: GiveAccessRequestMessage) {
        try {
            val user = requestService.getUserByEmail(message.emailId)
            val userAccesses = bookAccessRepository.findById(user.email)

            val issuedBook = IssuedBook(
                message.bookId,
                Date(System.currentTimeMillis()),
                Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15)
            )
            if (!userAccesses.isEmpty) {
                val newUserAccesses = userAccesses.get()
                newUserAccesses.issuedBooks.add(issuedBook)
                bookAccessRepository.save(newUserAccesses)
            } else {
                val bookAccess = BookAccess(user.email, mutableListOf(issuedBook))
                bookAccessRepository.insert(bookAccess)
            }

            serverLogRepository.insert(
                ServerLog(
                    ObjectId().toString(),
                    LogActions.ISSUE_ACCESS.toString(),
                    "Access issued for email: ${user.email}, on book id ${issuedBook.bookId} ",
                    Date(System.currentTimeMillis())
                )
            )
        } catch (e: Exception) {
            throw IssueAccessException("An exception occurred while issuing the book access: $e")
        }
    }

    @Throws(AccessException::class, BookException::class)
    override fun getIssuedBooksByEmailId(emailId: String): MutableList<IssuedBook> {
        return try {
            val accesses = bookAccessRepository.findById(emailId).get()
            accesses.issuedBooks
        } catch (e: Exception) {
            when (e) {
                is NoSuchElementException -> {
                    mutableListOf()
                }
                else -> {
                    throw GetAccessException("An exception occurred while getting the book accesses: $e")
                }
            }
        }
    }

    override fun getAllBookRequests(): List<BookAccessRequest> {
        try {
            return accessRequestRepository.findAll()
        } catch (e: Exception) {
            throw GetAccessException("An Exception Occurred while getting book requests ${e.message}")
        }
    }

    override fun getBookAccessRequestsByEmailId(emailId: String): List<BookAccessRequest> {
        return accessRequestRepository.findAllByEmailId(emailId)
    }

    override fun getServerLogs(): List<ServerLog> {
        return serverLogRepository.findAll()
    }

    @Throws(AccessException::class, UserException::class)
    override fun updateAccess(file: MultipartFile): String {
        try {
            val csvStream = file.inputStream

            val settings = CsvParserSettings()
            settings.format.setLineSeparator("\n")

            val parser = CsvParser(settings)
            val allRows: List<Array<String>> = parser.parseAll(InputStreamReader(csvStream, "UTF-8"))

            for (row in allRows) {
                val userEmail = row[0]
                requestService.getUserByEmail(userEmail)
                val issuedBookJson = row[1].replace("|||", ",")

                if (issuedBookJson != "[]") {
                    val gson = GsonBuilder().create()
                    val issuedBooks = gson.fromJson(issuedBookJson, Array<IssuedBook>::class.java).toMutableList()

                    bookAccessRepository.save(
                        BookAccess(
                            userEmail,
                            issuedBooks
                        )
                    )
                } else {
                    if (bookAccessRepository.existsById(userEmail)) {
                        bookAccessRepository.deleteById(userEmail)
                    }
                }
            }

            serverLogRepository.insert(
                ServerLog(
                    ObjectId().toString(),
                    LogActions.ACCESS_UPDATE.toString(),
                    "Accesses updated via CSV",
                    Date(System.currentTimeMillis())
                )
            )

            return "Accesses updated successfully"
        } catch (e: Exception) {
            println(e.message)
            throw UpdateAccessException("An exception occurred while updating accesses $e")
        }
    }

    @Throws(AccessException::class)
    override fun requestAccess(message: RequestBookAccessMessage): String {
        try {
            accessRequestRepository.insert(
                BookAccessRequest(
                    ObjectId().toString(),
                    message.bookId,
                    message.emailId
                )
            )

            serverLogRepository.insert(
                ServerLog(
                    ObjectId().toString(),
                    LogActions.ISSUE_REQUEST.toString(),
                    "Book ${message.bookId} access requested by ${message.emailId}",
                    Date(System.currentTimeMillis())
                )
            )
            return "Request issue for book ${message.bookId} with user ${message.emailId}"
        } catch (e: Exception) {
            throw RequestAccessException("An exception occurred while requesting the book access: $e")
        }
    }

    @Throws(AccessException::class)
    override fun approveAccess(message: ApproveAccessRequestMessage): String {
        try {
            accessRequestRepository.deleteById(message.requestId)
            issueAccess(
                GiveAccessRequestMessage(
                    message.bookId,
                    message.emailId
                )
            )

            serverLogRepository.insert(
                ServerLog(
                    ObjectId().toString(),
                    LogActions.APPROVE_ACCESS.toString(),
                    "Book Access ${message.bookId} approved for ${message.emailId} by admin ${serverUser.email}",
                    Date(System.currentTimeMillis())
                )
            )

            return "Access Approved to ${message.emailId} of book with id ${message.bookId}"
        } catch (e: Exception) {
            throw ApproveAccessException("An exception occurred while approving the book access $e")
        }
    }

    @Throws(AccessException::class)
    override fun rejectAccess(message: RejectAccessRequestMessage): String {
        try {
            accessRequestRepository.deleteById(message.requestId)

            serverLogRepository.insert(
                ServerLog(
                    ObjectId().toString(),
                    LogActions.ACCESS_DECLINED.toString(),
                    "Book Access ${message.bookId} rejected for ${message.emailId} by admin ${serverUser.email}",
                    Date(System.currentTimeMillis())
                )
            )

            return "Access Request rejected successfully requestId: ${message.requestId} of user ${message.emailId}"
        } catch (e: Exception) {
            println(e)
            throw RejectAccessException("An exception occurred while rejecting the book access with id ${message.requestId}")
        }
    }
}