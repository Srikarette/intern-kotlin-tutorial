package com.example.tutorial.entity.viewmodel

import java.util.UUID

interface UserOrdersGetProjection {
    val id: UUID
    val fullName: String
    val totalOrder: Long?
    val orderNumber: Long?
    val orderName: String?
    val orderPrice: Long?
    val orderAddress: String?
    val orderDateTime: String?
    val orderStatus: String?
}


