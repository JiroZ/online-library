package com.onlinelibrary.bookservice.services.bookservice

import com.onlinelibrary.bookservice.dto.messages.Messages
import com.onlinelibrary.bookservice.dto.user.ServerUser
import org.junit.Before
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.event.annotation.AfterTestClass
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


@SpringBootTest
internal class DefaultBookServiceTest(@Autowired val bookService: BookService, @Autowired val serverUser: ServerUser) {

    val registrationRequest: Messages.UserRegistrationRequest =
        Messages.UserRegistrationRequest("bhavishya20@gmail.com", "bhavishya", "1234")
    val authRequest = Messages.UserAuthRequest("bhavishya20@gmail.com", "1234",)
    val authWrongRequest = Messages.UserAuthRequest("bhavishya20@gmail.com", "122234",)

    var result: MultipartFile? = null

    @BeforeEach
    fun `init Server User before every method`() {
        serverUser.email = "bhavishya20@gmail.com"
        serverUser.isAdmin = true
    }

    @AfterEach
    fun `init Server User After every method`() {
        serverUser.email = "bhavishya20@gmail.com"
        serverUser.isAdmin = true
    }


    @BeforeEach
    fun `init test book`() {
        val path: Path = Paths.get("/resources/test/test_file.pdf")
        val name = "file.pdf"
        val originalFileName = "file.pdf"
        val contentType = "pdf"
        var content: ByteArray? = null
        try {
            content = Files.readAllBytes(path)
        } catch (_: IOException) {
        }
        result = MockMultipartFile(
            name, originalFileName, contentType, content
        )
    }

    @Test
    fun `Check if you can add book as admin`() {
        val book = Messages.AddBookRequestMessage("Test Title", "Test description", result!!)
        val message = bookService.addBook(book)

        assertEquals("Book ${book.bookTitle} Added Successfully", message.notice)
    }

    @Test
    fun `Check if you can delete a book as admin`() {
        val book = Messages.AddBookRequestMessage("Test Title", "Test description", result!!)
        val addMessage = bookService.addBook(book)
        val deleteMessage = bookService.deleteBook(addMessage.bookId)

        assertEquals("Book ${book.bookTitle} Deleted Successfully", deleteMessage.notice)
    }


}