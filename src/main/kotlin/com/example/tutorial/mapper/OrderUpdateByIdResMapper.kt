package com.example.tutorial.mapper

import com.example.tutorial.dto.OrderUpdateByIdRes
import com.example.tutorial.dto.enums.OrderStatus
import com.example.tutorial.entity.Orders

class OrderUpdateByIdResMapper {
    companion object {
        fun toOrderUpdateByIdRes(order: Orders): OrderUpdateByIdRes {
            return OrderUpdateByIdRes(
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