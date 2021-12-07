package com.onlinelibrary.bookservice.services.bookservice

import com.onlinelibrary.bookservice.dto.book.Book
import com.onlinelibrary.bookservice.dto.messages.Messages.*
import com.onlinelibrary.bookservice.dto.user.ServerUser
import com.onlinelibrary.bookservice.exceptions.*
import com.onlinelibrary.bookservice.respositories.BookRepository
import com.onlinelibrary.bookservice.services.requestservice.RequestService
import com.onlinelibrary.bookservice.services.s3service.DefaultS3Service
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class DefaultBookService(
    val s3Service: DefaultS3Service,
    val bookRepository: BookRepository,
    val requestService: RequestService,
    val serverUser: ServerUser
) : BookService {

    @Throws(BookException::class)
    override fun addBook(message: AddBookRequestMessage): BookAddResponseMessage {
        try {
            val file = s3Service.convertMultiPartFileToFile(message.bookFile)
            val assignedId = ObjectId().toString()

            val book = Book(
                assignedId,
                message.bookTitle,
                message.bookDescription,
                Date(System.currentTimeMillis()),
                message.bookFile.originalFilename!!
            )
            s3Service.uploadFile(file, assignedId, book.bookTitle)
            bookRepository.save(book)

            return BookAddResponseMessage(assignedId, "Book ${book.bookTitle} Added Successfully")
        } catch (e: Exception) {
            println(e.message)
            throw BookAddException("An exception occurred while adding the book $e")
        }
    }

    @Throws(BookException::class)
    override fun deleteBook(id: String): BookDeleteResponseMessage {
        try {
            val book = bookRepository.findById(id).get()
            s3Service.deleteFile(book.fileName, book.id)

            return BookDeleteResponseMessage(
                book.id,
                "Book ${book.bookTitle} Deleted Successfully"
            )
        } catch (e: Exception) {
            println(e.message)
            throw BookDeleteException("An exception occurred while deleting the book $e")
        }
    }

    @Throws(BookException::class, AccessException::class)
    override fun getBook(bookId: String): BookResponseMessage {
        try {
            val email = serverUser.email

            checkForBookExpiry(email, bookId)

            val book = bookRepository.findById(bookId).get()
            return BookResponseMessage(
                book
            )
        } catch (e: Exception) {
            println(e.message)
            throw BookGetException("An exception occurred while getting the book $e")
        }
    }

    @Throws(BookException::class)
    override fun getAllBooks(): List<Book> {
        try {
            return bookRepository.findAll()
        } catch (e: Exception) {
            println(e.message)
            throw BookNotFoundException("An exception occurred while getting all books")
        }
    }

    @Throws(BookException::class)
    override fun downloadBook(bookId: String): BookFileResponseMessage {
        try {
            val email = serverUser.email

           checkForBookExpiry(email, bookId)

            val book = bookRepository.findById(bookId).get()
            val bookFile = s3Service.downloadFile(book.fileName, book.id)
            return BookFileResponseMessage(
                book,
                bookFile
            )
        } catch (e: Exception) {
            println(e.message)
            throw BookGetException("An exception occurred while getting the book $e")
        }
    }

    fun checkForBookExpiry(email: String, bookId: String) {
        val issuedBooks = requestService.getIssuedBooksByEmail(email)

        for (book in issuedBooks) {

                if (book.bookId == bookId && Date(System.currentTimeMillis()).after(book.expDate)) {
                    requestService.revokeAccessRequest(RevokeAccessRequestMessage(
                        book.bookId,
                        email
                    ))
                    throw InvalidAccessException("Your book issue period is expired please reissue book to continue reading")
         }
        }
    }
}