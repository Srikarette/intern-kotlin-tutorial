package com.example.tutorial.constant

enum class ResponseConstants(private val statusCode: Int, private val statusMessage: String) {
    // get user
    GET_USER_NOT_FOUND(4000,"GET USER NOT FOUND"),
    GET_ORDER_NOT_FOUND(4000,"GET ORDER NOT FOUND");

    fun getCode(): Int {
        return statusCode
    }

    fun getMessage(): String {
        return statusMessage
    }
}