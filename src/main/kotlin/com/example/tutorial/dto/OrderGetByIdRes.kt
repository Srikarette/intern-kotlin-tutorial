package com.example.tutorial.dto

import com.example.tutorial.dto.enums.OrderStatus
import java.time.LocalDateTime
import java.util.UUID

data class OrderGetByIdRes(
    val id: UUID,
    val name: String?,
    val price: Long?,
    val address: String?,
    val orderDateTime: LocalDateTime?,
    val orderStatus: OrderStatus,
    val userId: UUID?
)