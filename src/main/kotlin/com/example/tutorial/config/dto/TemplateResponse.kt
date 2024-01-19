package com.example.tutorial.config.dto

import com.example.tutorial.config.constant.AppConfigConstants.Companion.SUCCESS_CODE
import com.example.tutorial.config.constant.AppConfigConstants.Companion.SUCCESS_MESSAGE

data class TemplateResponse(
    val statusCode: Int,
    val message: String,
    val translationKeys: List<String> = listOf(),
    val data: Any? = null,
) {

    constructor() : this(null)

    constructor(data: Any?) : this(
        statusCode = SUCCESS_CODE,
        message = SUCCESS_MESSAGE,
        data = data
    )

}