package com.example.tutorial

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class TutorialApplication

fun main(args: Array<String>) {
    runApplication<TutorialApplication>(*args)
}
