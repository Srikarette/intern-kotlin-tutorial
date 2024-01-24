package com.example.tutorial.controller

import com.example.tutorial.dto.OrderCreateReq
import com.example.tutorial.dto.OrderCreateRes
import com.example.tutorial.dto.OrderDeleteByIdReq
import com.example.tutorial.dto.OrderDeleteByIdRes
import com.example.tutorial.dto.OrderGetByIdReq
import com.example.tutorial.dto.OrderGetByIdRes
import com.example.tutorial.dto.OrderListGetRes
import com.example.tutorial.dto.OrderUpdateByIdReq
import com.example.tutorial.dto.OrderUpdateByIdRes
import com.example.tutorial.mapper.OrderCreateResMapper
import com.example.tutorial.mapper.OrderDeleteByIdResMapper
import com.example.tutorial.mapper.OrderGetByIdResMapper
import com.example.tutorial.mapper.OrderListGetResMapper
import com.example.tutorial.mapper.OrderUpdateByIdResMapper
import com.example.tutorial.service.OrderService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(private val orderService: OrderService) {

    @PostMapping("/")
    fun getOrderList(): List<OrderListGetRes> {
        val orders = orderService.getOrders()
        return OrderListGetResMapper.toOrderListGetRes(orders)
    }

    @PostMapping("/get")
    fun getOrderById(@Valid @RequestBody req: OrderGetByIdReq): OrderGetByIdRes {
        val order = orderService.getOrderById(req)
        return OrderGetByIdResMapper.toOrderGetByIdRes(order)
    }

    @PostMapping("/create")
    fun updateOrderById(@Valid @RequestBody req: OrderCreateReq): OrderCreateRes {
        val order = orderService.createOrder(req)
        return OrderCreateResMapper.toOrderCreateRes(order)
    }

    @PostMapping("/update")
    fun putUser(@Valid @RequestBody req: OrderUpdateByIdReq): OrderUpdateByIdRes {
        val orderToUpdate = orderService.updateOrderById(req)
        return OrderUpdateByIdResMapper.toOrderUpdateByIdRes(orderToUpdate)
    }

    @PostMapping("/delete")
    fun deleteUser(@Valid @RequestBody req: OrderDeleteByIdReq): OrderDeleteByIdRes {
        val orderToDelete = orderService.deleteOrderById(req)
        return OrderDeleteByIdResMapper.toOrderDeleteByIdRes(orderToDelete)
    }

}