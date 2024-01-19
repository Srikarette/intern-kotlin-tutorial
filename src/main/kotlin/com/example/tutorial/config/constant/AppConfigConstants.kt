package com.example.tutorial.config.constant

class AppConfigConstants private constructor() {
    companion object {
        // Template Response
        const val SUCCESS_CODE = 1000
        const val SUCCESS_MESSAGE = "Success"

        // Error
        const val ERR_INTERNAL_SERVER_CODE = 5000
        const val ERR_INTERNAL_SERVER_MESSAGE = "Internal server error"

        const val ERR_UNABLE_TO_PROCEED_CODE = 4000
        const val ERR_UNABLE_TO_PROCEED_MESSAGE = "Unable to proceed"

        const val ERR_INVALID_REQUEST_CODE = 4000
        const val ERR_INVALID_REQUEST_MESSAGE = "Invalid Request"
    }
}