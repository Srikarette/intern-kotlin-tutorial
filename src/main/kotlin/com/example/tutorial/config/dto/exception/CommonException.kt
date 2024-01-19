package com.example.tutorial.config.dto.exception

data class CommonException(
    val errorCode: Int,
    val errorMessage: String
) : RuntimeException()