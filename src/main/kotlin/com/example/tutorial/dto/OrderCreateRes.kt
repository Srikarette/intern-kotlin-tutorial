package com.example.tutorial.dto

import com.example.tutorial.dto.enums.OrderStatus
import java.time.LocalDate
import java.util.UUID

data class OrderCreateRes(
    val id: UUID,
    val name: String? = null,
    val price: Double? = null,
    val address: String? = null,
    val orderDateTime: LocalDate? = null,
    val orderStatus: OrderStatus,
    val userId: UUID? = null,
)
