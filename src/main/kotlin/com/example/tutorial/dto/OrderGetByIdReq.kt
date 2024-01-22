package com.example.tutorial.dto

import com.example.tutorial.dto.enums.OrderStatus
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.UUID

data class OrderGetByIdReq (
    @field:NotNull(message = "order name must not be null")
    val id: UUID? = null,
)