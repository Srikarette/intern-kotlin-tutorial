package com.example.tutorial.dto

import com.example.tutorial.dto.enums.OrderStatus
import java.time.LocalDate
import java.util.UUID

data class UserGetOrdersRes(
    val id: UUID,
    val fullName: String,
    val totalOrder: Int?,
    val userOrders: List<UserData>
) {
    data class UserData(
        val orderList: List<OrderData>?
    )

    data class OrderData(
        val orderNumber: Int?,
        val orderName: String?,
        val orderPrice: Double?,
        val orderAddress: String?,
        val orderDateTime: String?,
        val orderStatus: OrderStatus
    )
}