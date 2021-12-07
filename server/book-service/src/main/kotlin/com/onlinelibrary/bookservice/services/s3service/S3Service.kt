package com.onlinelibrary.bookservice.services.s3service

import java.io.File

interface S3Service {

    fun uploadFile(file: File, assignedId: String,  bookTitle: String): String
    fun deleteFile(name: String, fileId: String): String
    fun downloadFile(name: String, fileId: String): ByteArray
    fun getAllS3Objects()
}