package com.example.tutorial.dto

import com.example.tutorial.dto.enums.OrderStatus
import com.example.tutorial.dto.enums.OrderStatus.PENDING
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.util.UUID

data class OrderCreateReq(
    @field:NotNull(message = "order name must not be null")
    @field:Size(max = 50, message = "order name must no longer than 50 word")
    val name: String?,
    @field:NotNull(message = "order price must not be null")
    @field:Max(value = 999999, message = "order price must not exceed 999,999")
    val price: Double?,
    val address: String?,
    val orderDateTime: LocalDate?,
    val orderStatus: OrderStatus? = PENDING,
    @field:NotNull(message = "order by must not be null")
    val userId: UUID?
)