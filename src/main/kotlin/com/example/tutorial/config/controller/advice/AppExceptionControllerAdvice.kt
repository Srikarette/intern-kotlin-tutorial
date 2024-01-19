package com.example.tutorial.config.controller.advice

import com.example.tutorial.config.constant.AppConfigConstants.Companion.ERR_INTERNAL_SERVER_CODE
import com.example.tutorial.config.constant.AppConfigConstants.Companion.ERR_INTERNAL_SERVER_MESSAGE
import com.example.tutorial.config.constant.AppConfigConstants.Companion.ERR_INVALID_REQUEST_CODE
import com.example.tutorial.config.constant.AppConfigConstants.Companion.ERR_INVALID_REQUEST_MESSAGE
import com.example.tutorial.config.constant.AppConfigConstants.Companion.ERR_UNABLE_TO_PROCEED_CODE
import com.example.tutorial.config.constant.AppConfigConstants.Companion.ERR_UNABLE_TO_PROCEED_MESSAGE
import com.example.tutorial.config.dto.TemplateResponse
import com.example.tutorial.config.dto.exception.CommonException
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AppExceptionControllerAdvice {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handleException(exception: Exception): TemplateResponse {
        exception.printStackTrace()
        return TemplateResponse(
            statusCode = ERR_INTERNAL_SERVER_CODE,
            message = ERR_INTERNAL_SERVER_MESSAGE
        )
    }

    @ExceptionHandler(CommonException::class)
    fun handleCommonException(commonException: CommonException): TemplateResponse {
        return TemplateResponse(
            statusCode = commonException.errorCode,
            message = commonException.errorMessage
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(exception: Exception): TemplateResponse {
        return TemplateResponse(
            statusCode = ERR_INVALID_REQUEST_CODE,
            message = ERR_INVALID_REQUEST_MESSAGE,
        )
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(bindException: BindException): TemplateResponse {
        return TemplateResponse(
            statusCode = ERR_UNABLE_TO_PROCEED_CODE,
            message = ERR_UNABLE_TO_PROCEED_MESSAGE,
            translationKeys = bindException.fieldErrors
                .mapNotNull { it.defaultMessage }
                .toList()
        )
    }

}