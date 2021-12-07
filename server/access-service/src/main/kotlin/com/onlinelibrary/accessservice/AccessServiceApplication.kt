package com.onlinelibrary.accessservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AccessServiceApplication

fun main(args: Array<String>) {
    runApplication<AccessServiceApplication>(*args)
}
