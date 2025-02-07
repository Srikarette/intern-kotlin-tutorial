package com.example.tutorial.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class UserDeleteByIdReq(
    @field:NotNull(message = "userId must not be null")
    val userId: UUID? = null,
    @field:NotNull(message = "email must not be null")
    @field:Size(max = 255, message = "email must no longer than 255 word")
    val email: String?,
)
