package com.example.tutorial.mapper

import com.example.tutorial.dto.OrderGetByIdRes
import com.example.tutorial.dto.UserGetByIdRes
import com.example.tutorial.entity.Orders
import com.example.tutorial.entity.User

class OrderGetByIdResMapper private constructor(){
    companion object{
        fun toOrderGetByIdRes(order: Orders): OrderGetByIdRes {
            return OrderGetByIdRes(
                id =order.id!!,
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