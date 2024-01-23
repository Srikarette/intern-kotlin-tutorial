package com.example.tutorial.mapper

import com.example.tutorial.dto.OrderListGetRes
import com.example.tutorial.entity.Orders

class OrderListGetResMapper private constructor() {
    companion object {
        fun toOrderListGetRes(orders: List<Orders>): List<OrderListGetRes> {
            return orders.map { toOrderList(it) }
        }

        private fun toOrderList(order: Orders): OrderListGetRes {
            return OrderListGetRes(
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