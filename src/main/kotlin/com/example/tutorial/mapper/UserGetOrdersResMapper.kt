package com.example.tutorial.mapper

import com.example.tutorial.dto.UserGetOrdersRes
import com.example.tutorial.entity.viewmodel.UserOrdersGetProjection

class UserGetOrdersResMapper private constructor() {
    companion object {
        fun toUserGetOrdersRes(users: List<UserOrdersGetProjection>): UserGetOrdersRes {
            return UserGetOrdersRes(
                id = users[0].id,
                fullName = users[0].fullName,
                totalOrder = users.get(0).totalOrder,
                userOrders = users.map { toOrderProjection(it) }
            )
        }

        private fun toOrderProjection(user: UserOrdersGetProjection): UserGetOrdersRes.UserData {
            return UserGetOrdersRes.UserData(
                orderList = listOf(
                    UserGetOrdersRes.OrderData(
                        orderNumber = user.orderNumber,
                        orderName = user.orderName,
                        orderPrice = user.orderPrice,
                        orderAddress = user.orderAddress,
                        orderDateTime = user.orderDateTime,
                        orderStatus = user.orderStatus
                    )
                )
            )
        }
    }
}
