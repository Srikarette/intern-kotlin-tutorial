package com.example.tutorial.constant

enum class ResponseConstants(private val statusCode: Int, private val statusMessage: String) {
    // get user
    GET_ALL_USER_SUCCESS(2000,"GET ALL USER SUCCESS"),
    GET_USER_NOT_FOUND(4000,"GET USER NOT FOUND"),
    CREATE_USER_SUCCESS(2000,"CREATE USER SUCCESS"),
    UPDATE_USER_SUCCESS(2000,"UPDATE USER SUCCESS"),
    DELETE_USER_SUCCESS(2000,"DELETE USER SUCCESS"),

    // validation error
    VALIDATION_ERROR(5000,"VALIDATION ERROR");


    fun getCode(): Int {
        return statusCode
    }

    fun getMessage(): String {
        return statusMessage
    }
}