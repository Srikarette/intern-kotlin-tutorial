package com.example.tutorial.service

import com.example.tutorial.config.dto.exception.CommonException
import com.example.tutorial.constant.ResponseConstants.GET_ORDER_NOT_FOUND
import com.example.tutorial.constant.ResponseConstants.GET_USER_NOT_FOUND
import com.example.tutorial.dto.OrderCreateReq
import com.example.tutorial.dto.OrderDeleteByIdReq
import com.example.tutorial.dto.OrderGetByIdReq
import com.example.tutorial.dto.OrderUpdateByIdReq
import com.example.tutorial.entity.Orders
import com.example.tutorial.repository.OrderRepository
import com.example.tutorial.repository.UserRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository
) {
    private fun validateUserIdExists(userId: UUID) {
        if (!userRepository.existsById(userId)) {
            throw CommonException(GET_USER_NOT_FOUND.getCode(), GET_USER_NOT_FOUND.getMessage())
        }
    }

    private fun validateOrderIdExists(id: UUID) {
        if (!orderRepository.existsById(id)) {
            throw CommonException(GET_ORDER_NOT_FOUND.getCode(), GET_ORDER_NOT_FOUND.getMessage())
        }
    }

    @Cacheable(value = ["orderList"], unless = "#result==null")
    fun getOrders(): List<Orders> {
        return orderRepository.findAll()
    }

    @Cacheable(value = ["order"], key = "#req.id")
    fun getOrderById(req: OrderGetByIdReq): Orders {
        validateOrderIdExists(req.id!!)
        val existsUser = orderRepository.findById(req.id)
        return existsUser.get()
    }

    fun createOrder(req: OrderCreateReq): Orders {
        validateUserIdExists(req.userId!!)
        val ordersCreated = Orders(
            name = req.name,
            price = req.price,
            address = req.address,
            orderDateTime = req.orderDateTime,
            orderStatus = req.orderStatus!!,
            userId = req.userId
        )
        return orderRepository.save(ordersCreated)
    }

    @CachePut(value = ["updatedOrder"], key = "#req.id")
    fun updateOrderById(req: OrderUpdateByIdReq): Orders {
        validateUserIdExists(req.userId!!)
        validateOrderIdExists(req.id!!)
        val existingOrder = orderRepository.findById(req.id)
        val original = existingOrder.get()
        val updatedOrder = original.copy(
            name = req.name ?: original.name,
            price = req.price ?: original.price,
            address = req.address ?: original.address,
            orderDateTime = req.orderDateTime ?: original.orderDateTime,
            orderStatus = req.orderStatus ?: original.orderStatus,
            userId = req.userId
        )

        return orderRepository.save(updatedOrder)
    }

    @CacheEvict(value = ["deletedOrder"], key = "#req.id", allEntries = true)
    fun deleteOrderById(req: OrderDeleteByIdReq): Orders {
        validateUserIdExists(req.userId!!)
        validateOrderIdExists(req.id!!)
        val existingOrder = orderRepository.findById(req.id)
        orderRepository.deleteById(req.id)
        return existingOrder.get()
    }

}