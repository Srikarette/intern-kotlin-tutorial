package com.example.tutorial.entity.viewmodel

import com.example.tutorial.dto.enums.OrderStatus
import java.time.LocalDate
import java.util.UUID

interface UserOrdersGetProjection {
    val id: UUID
    val fullName: String
    val totalOrder: Int?
    val orderNumber: Int?
    val orderName: String?
    val orderPrice: Double?
    val orderAddress: String?
    val orderDateTime: String?
    val orderStatus: OrderStatus?
}


