package com.example.tutorial.dto

import com.example.tutorial.dto.enums.OrderStatus
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.util.UUID

data class OrderUpdateByIdReq(
    @field:NotNull(message = "orderId must not be null")
    val id: UUID? = null,
    @field:Size(max = 50, message = "order name must no longer than 50 word")
    val name: String?,
    @field:Max(value = 999999, message = "order price must not exceed 999,999")
    val price: Double?,
    @field:Size(max = 255, message = "order address must no longer than 255 word")
    val address: String?,
    val orderDateTime: LocalDate?,
    val orderStatus: OrderStatus? = OrderStatus.PENDING,
    @field:NotNull(message = "order by must not be null")
    val userId: UUID?
)