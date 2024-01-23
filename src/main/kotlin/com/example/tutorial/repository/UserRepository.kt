package com.example.tutorial.repository

import com.example.tutorial.entity.User
import com.example.tutorial.entity.viewmodel.UserFullNameGetListProjection
import com.example.tutorial.entity.viewmodel.UserOrdersGetProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    @Query(
        """
            SELECT
                users.id AS id,
                CONCAT(users.first_name, ' ', users.last_name) AS fullName
            FROM
                users;
        """,
        nativeQuery = true,
    )
    fun getAllUsersFullName(): List<UserFullNameGetListProjection>

    @Query(
        """
            SELECT
                users.id AS id,
                CONCAT(users.first_name, ' ', users.last_name) AS fullName,
                COUNT(orders.order_id) OVER (PARTITION BY users.id) AS totalOrder,
                ROW_NUMBER() OVER (PARTITION BY users.id ORDER BY orders.order_id) AS orderNumber,
                orders.order_id AS orderId,
                orders.name AS orderName,
                orders.price AS orderPrice,
                orders.address AS orderAddress,
                orders.order_date AS orderDateTime,
                orders.order_status AS orderStatus
            FROM users
            LEFT JOIN orders ON users.id = orders.user_id
            WHERE users.id = (:userId)
            GROUP BY users.id, CONCAT(users.first_name, ' ', users.last_name), orders.order_id, orders.name, orders.price, orders.address, orders.order_date, orders.order_status
        """
        ,
        nativeQuery = true
    )
    fun getUserOrders(
        userId: UUID
    ): List<UserOrdersGetProjection>
}