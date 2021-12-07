package com.onlinelibrary.bookservice.services.bookservice

import com.onlinelibrary.bookservice.dto.book.Book
import com.onlinelibrary.bookservice.dto.messages.Messages
import com.onlinelibrary.bookservice.dto.messages.Messages.*
import com.onlinelibrary.bookservice.exceptions.BookException

interface BookService {
    fun addBook(message: AddBookRequestMessage): BookAddResponseMessage
    fun deleteBook(id: String): BookDeleteResponseMessage
    fun getBook(bookId: String): BookResponseMessage
    fun getAllBooks(): List<Book>
    fun downloadBook(bookId: String): BookFileResponseMessage
}