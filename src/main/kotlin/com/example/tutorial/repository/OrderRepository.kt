package com.example.tutorial.repository

import com.example.tutorial.entity.Orders
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderRepository : JpaRepository<Orders, UUID>