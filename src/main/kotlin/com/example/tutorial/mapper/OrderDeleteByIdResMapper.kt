package com.example.tutorial.mapper

import com.example.tutorial.dto.OrderDeleteByIdRes
import com.example.tutorial.entity.Orders

class OrderDeleteByIdResMapper private constructor() {
    companion object {
        fun toOrderDeleteByIdRes(order: Orders): OrderDeleteByIdRes {
            return OrderDeleteByIdRes(
                id = order.id!!,
                name = order.name,
                price = order.price,
                address = order.address,
                orderDateTime = order.orderDateTime,
                orderStatus = order.orderStatus,
                userId = order.userId
            )
        }
    }
}