package com.example.tutorial.dto

import com.example.tutorial.dto.enums.OrderStatus
import java.time.LocalDateTime
import java.util.UUID

data class OrderCreateRes(
    val id: UUID,
    val name: String?  = null,
    val price: Long? = null,
    val address: String? = null,
    val orderDateTime: LocalDateTime?  = null,
    val orderStatus: OrderStatus,
    val userId: UUID?  = null,
)
