package com.example.tutorial.entity

import com.example.tutorial.dto.enums.OrderStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "orders")
data class Orders(
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    @Column(name = "name")
    val name: String? = null,
    @Column(name = "price")
    val price: Double?,
    @Column(name = "address")
    val address: String?,
    @Column(name = "order_date")
    val orderDateTime: LocalDate?,
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus,
    @Column(name = "user_id")
    val userId: UUID? = null
) : Serializable