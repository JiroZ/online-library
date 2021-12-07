package com.onlinelibrary.bookservice.controllers

import com.onlinelibrary.bookservice.dto.book.Book
import com.onlinelibrary.bookservice.dto.messages.Messages.*
import com.onlinelibrary.bookservice.exceptions.AWSException
import com.onlinelibrary.bookservice.exceptions.AccessException
import com.onlinelibrary.bookservice.exceptions.BookException
import com.onlinelibrary.bookservice.exceptions.UserException
import com.onlinelibrary.bookservice.services.bookservice.BookService
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(val bookService: BookService) {
    @PostMapping("/add")
    @Throws(UserException::class, AWSException::class, AccessException::class, BookException::class)
    fun addBook(
        @ModelAttribute message: AddBookRequestMessage,
        bindingResult: BindingResult
    ): ResponseEntity<BookAddResponseMessage> {
        bindErrorResult(bindingResult)
        return ResponseEntity.ok(bookService.addBook(message))
    }

    @GetMapping("/delete/{bookId}")
    @Throws(UserException::class, AWSException::class, AccessException::class, BookException::class)
    fun deleteBook(
        @PathVariable bookId: String,
        bindingResult: BindingResult
    ): ResponseEntity<BookDeleteResponseMessage> {
        bindErrorResult(bindingResult)
        return ResponseEntity.ok(bookService.deleteBook(bookId))
    }

    @GetMapping("/{bookId}")
    @Throws(UserException::class, AWSException::class, AccessException::class, BookException::class)
    fun getBook(@PathVariable bookId: String): ResponseEntity<BookResponseMessage> {
        return ResponseEntity.ok(bookService.getBook(bookId))
    }

    @GetMapping("/download/{bookId}")
    fun getBookFile(@PathVariable bookId: String) : ResponseEntity<BookFileResponseMessage> {
        return ResponseEntity.ok(bookService.downloadBook(bookId))
    }

    @GetMapping
    @Throws(UserException::class, AWSException::class, AccessException::class, BookException::class)
    fun getBooks(): ResponseEntity<List<Book>> {
        return ResponseEntity.ok(bookService.getAllBooks())
    }

    private fun bindErrorResult(bindingResult: BindingResult) {
        val errorMessage = StringBuilder()
        if (bindingResult.hasErrors()) {
            val errors = bindingResult.fieldErrors
            for (error in errors) {
                errorMessage.append(error.defaultMessage!! + " ")
            }
            throw Exception(errorMessage.toString())
        }
    }
}