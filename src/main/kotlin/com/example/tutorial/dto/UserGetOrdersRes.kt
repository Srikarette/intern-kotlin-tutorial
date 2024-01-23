package com.example.tutorial.dto

import java.util.UUID

data class UserGetOrdersRes(
    val id: UUID,
    val fullName: String,
    val totalOrder: Long?,
    val userOrders: List<UserData>
) {
    data class UserData(
        val orderList: List<OrderData>?
    )

    data class OrderData(
        val orderNumber: Long?,
        val orderName: String?,
        val orderPrice: Long?,
        val orderAddress: String?,
        val orderDateTime: String?,
        val orderStatus: String?
    )
}