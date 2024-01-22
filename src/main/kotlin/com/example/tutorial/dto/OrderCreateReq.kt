package com.example.tutorial.dto

import com.example.tutorial.dto.enums.OrderStatus
import com.example.tutorial.dto.enums.OrderStatus.PENDING
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.UUID

data class OrderCreateReq(
    @field:NotNull(message = "order name must not be null")
    @field:Size(max = 50, message = "order name must no longer than 50 word")
    val name: String?,
    @field:NotNull(message = "order price must not be null")
    @field:Max(value = 50, message = "order price must not exceed 50")
    val price: Long?,
    val address: String?,
    val orderDateTime: LocalDateTime?,
    val orderStatus: OrderStatus? = PENDING,
    @field:NotNull(message = "order by must not be null")
    val userId: UUID?
)