package com.example.tutorial.dto

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class OrderGetByIdReq(
    @field:NotNull(message = "orderId must not be null")
    val id: UUID? = null,
)