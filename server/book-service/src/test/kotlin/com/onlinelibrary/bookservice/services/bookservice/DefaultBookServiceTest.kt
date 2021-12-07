package com.onlinelibrary.bookservice.services.bookservice

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class DefaultBookServiceTest(@Autowired val bookService: BookService) {
    @Test
    fun `Check if you can fetch book from database`() {
        val bookId = "61a868cfecddc61780b9d95b"
        val bookMessage = bookService.getBook(bookId)

        assertEquals(bookMessage.book.id, bookId)
    }
}