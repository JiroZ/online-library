package com.onlinelibrary.bookservice.exceptions

sealed class AWSException(message: String) : Exception(message)

class S3UploadException(message: String) : AWSException(message)
class S3DownloadException(message: String) : AWSException(message)
class S3UpdateException(message: String) : AWSException(message)
class S3GetException(message: String) : AWSException(message)