package com.example.tutorial.dto

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class UserGetByIdReq(
    @field:NotNull(message = "userId must not be null")
    val userId: UUID? = null
)
