package com.example.tutorial.dto

import com.example.tutorial.dto.enums.OrderStatus
import java.time.LocalDate
import java.util.UUID

data class OrderDeleteByIdRes(
    val id: UUID,
    val name: String?,
    val price: Double?,
    val address: String?,
    val orderDateTime: LocalDate?,
    val orderStatus: OrderStatus,
    val userId: UUID?
)