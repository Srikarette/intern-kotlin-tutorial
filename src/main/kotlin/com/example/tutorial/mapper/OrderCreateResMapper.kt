package com.example.tutorial.mapper

import com.example.tutorial.dto.OrderCreateRes
import com.example.tutorial.entity.Orders

class OrderCreateResMapper private constructor() {
    companion object {
        fun toOrderCreateRes(order: Orders): OrderCreateRes {
            return OrderCreateRes(
                id = order.id!!,
                name = order.name,
                price = order.price,
                address = order.address,
                orderDateTime = order.orderDateTime,
                orderStatus = order.orderStatus,
                userId = order.userId,
            )
        }
    }
}