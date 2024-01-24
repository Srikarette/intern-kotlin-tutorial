package com.example.tutorial.dto

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class OrderDeleteByIdReq(
    @field:NotNull(message = "orderId must not be null")
    val id: UUID? = null,

    @field:NotNull(message = "order by must not be null")
    val userId: UUID?
)