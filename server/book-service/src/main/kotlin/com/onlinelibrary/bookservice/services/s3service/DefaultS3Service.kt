package com.onlinelibrary.bookservice.services.s3service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.iterable.S3Objects
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.model.S3ObjectInputStream
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.amazonaws.util.IOUtils
import com.onlinelibrary.bookservice.exceptions.AWSException
import com.onlinelibrary.bookservice.exceptions.S3DownloadException
import com.onlinelibrary.bookservice.exceptions.S3GetException
import com.onlinelibrary.bookservice.exceptions.S3UploadException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@Service
class DefaultS3Service(
    val s3Client: AmazonS3
) : S3Service {

    @Value("\${application.bucket.name}")
    private val bucketName: String? = null

    @Throws(AWSException::class)
    override fun uploadFile(file: File, assignedId: String, bookTitle: String): String {
        try {
            val fileName = assignedId + "_" + file.name
            s3Client.putObject(PutObjectRequest(bucketName, fileName, file))
            file.delete()
            return "File uploaded : $fileName"
        } catch(e: Exception) {
            throw S3UploadException("Error while uploading file to AWS S3 $e")
        }
    }

    @Throws(AWSException::class)
    override fun downloadFile(name: String, fileId: String): ByteArray {
        try {
            val fileName = fileId + "_" + name
            val s3Object: S3Object = s3Client.getObject(bucketName, fileName)
            val inputStream: S3ObjectInputStream = s3Object.objectContent

            return IOUtils.toByteArray(inputStream)

        } catch(e: Exception) {
            throw S3DownloadException("Error while downloading file from AWS S3 $e")
        }
    }

    @Throws(AWSException::class)
    override fun deleteFile(name: String, fileId: String): String {
        try {
            val fileName = fileId + "_" + name
            s3Client.deleteObject(bucketName, fileName)
            return "$fileName removed ..."
        } catch(e: Exception) {
            throw S3DownloadException("Error while deleting file from AWS S3 $e")
        }
    }

    @Throws(AWSException::class)
    override fun getAllS3Objects() {
        try {
            val objects = S3Objects.inBucket(s3Client, bucketName)
            for (value in objects) {
                println(value)
            }
        } catch (e: Exception) {
            throw S3GetException("Error while getting files from S3 $e")
        }
    }


    fun convertMultiPartFileToFile(file: MultipartFile): File {
        val convertedFile = File(file.originalFilename!!)
        try {
            FileOutputStream(convertedFile).use { fos -> fos.write(file.bytes) }
        } catch (e: IOException) {
            println("Error converting multipartFile to file: $e")
        }
        return convertedFile
    }
}