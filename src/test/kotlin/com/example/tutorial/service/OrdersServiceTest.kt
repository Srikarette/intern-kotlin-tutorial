package com.example.tutorial.service

import com.example.tutorial.config.dto.exception.CommonException
import com.example.tutorial.constant.ResponseConstants.GET_ORDER_NOT_FOUND
import com.example.tutorial.constant.ResponseConstants.GET_USER_NOT_FOUND
import com.example.tutorial.dto.OrderCreateReq
import com.example.tutorial.dto.OrderDeleteByIdReq
import com.example.tutorial.dto.OrderGetByIdReq
import com.example.tutorial.dto.OrderUpdateByIdReq
import com.example.tutorial.dto.enums.OrderStatus.PROCESSING
import com.example.tutorial.entity.Orders
import com.example.tutorial.repository.OrderRepository
import com.example.tutorial.repository.UserRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.Optional
import java.util.UUID

class OrdersServiceTest {
    private val userRepository = mockk<UserRepository>()
    private val orderRepository = mockk<OrderRepository>()
    private val orderService = spyk(OrderService(orderRepository, userRepository))

    private val userId = UUID.randomUUID()
    private val orderId = UUID.randomUUID()

    @Nested
    @DisplayName("getOrders()")
    inner class GetOrdersTest {
        private val orders = listOf(
            Orders(
                id = orderId,
                name = "order object 1",
                price = 355.5,
                address = "address at test street",
                orderDateTime = LocalDate.now(),
                orderStatus = PROCESSING,
                userId = userId
            )
        )
        private val req = OrderGetByIdReq(
            id = orderId
        )
        private val orderData = Orders(
            id = orderId,
            name = "order object 1",
            price = 355.0,
            address = "address at test street",
            orderDateTime = LocalDate.now(),
            orderStatus = PROCESSING,
            userId = userId
        )
        private val expectedResult = Orders(
            id = orderId,
            name = "order object 1",
            price = 355.0,
            address = "address at test street",
            orderDateTime = LocalDate.now(),
            orderStatus = PROCESSING,
            userId = userId
        )

        @Test
        fun `GIVEN orders WHEN getOrderList THEN success`() {
            // Given
            every { orderRepository.findAll() } returns orders

            // When
            val actualResult = orderService.getOrders()

            // Then
            Assertions.assertEquals(orders, actualResult)
            verify(exactly = 1) { orderRepository.findAll() }
        }

        @Test
        fun `GIVEN order WHEN getOrderById THEN success`() {
            // Given
            every { orderRepository.existsById(req.id!!) } returns true
            every { orderRepository.findById(req.id!!) } returns Optional.of(orderData)

            // When
            val actualResult = orderService.getOrderById(req)

            // Then
            Assertions.assertEquals(expectedResult, actualResult)
            verify(exactly = 1) { orderRepository.existsById(req.id!!) }
            verify(exactly = 1) { orderRepository.findById(req.id!!) }
        }

        @Test
        fun `GIVEN invalid order WHEN getOrderById THEN throw`() {
            // Given
            val expectedException = CommonException(
                GET_ORDER_NOT_FOUND.getCode(),
                GET_ORDER_NOT_FOUND.getMessage()
            )
            every { orderRepository.existsById(req.id!!) } returns false
            every { orderRepository.findById(req.id!!) } returns Optional.of(orderData)
            // When
            val actualException = Assertions.assertThrows(CommonException::class.java) {
                orderService.getOrderById(req)
            }

            // Then
            Assertions.assertEquals(expectedException, actualException)
            verify(exactly = 1) { orderRepository.existsById(req.id!!) }
            verify(exactly = 0) { orderRepository.findById(req.id!!) }
        }
    }

    @Nested
    @DisplayName("createOrders()")
    inner class CreateOrderTest {
        private val req = OrderCreateReq(
            name = "order object 1",
            price = 355.5,
            address = "address at test street",
            orderDateTime = LocalDate.now(),
            orderStatus = PROCESSING,
            userId = userId
        )
        private val orderData = Orders(
            name = "order object 1",
            price = 355.5,
            address = "address at test street",
            orderDateTime = LocalDate.now(),
            orderStatus = PROCESSING,
            userId = userId
        )
        private val expectedResult = Orders(
            name = "order object 1",
            price = 355.5,
            address = "address at test street",
            orderDateTime = LocalDate.now(),
            orderStatus = PROCESSING,
            userId = userId
        )

        @Test
        fun `GIVEN order WHEN createOrder THEN success`() {
            // Given
            every { userRepository.existsById(req.userId!!) } returns true
            every { orderRepository.save(orderData) } returns orderData
            // When
            val actualResult = orderService.createOrder(req)

            // Then
            Assertions.assertEquals(expectedResult, actualResult)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 1) { orderRepository.save(orderData) }
        }

        @Test
        fun `GIVEN invalid userId WHEN createOrder THEN throw`() {
            // Given
            val expectedException = CommonException(
                GET_USER_NOT_FOUND.getCode(),
                GET_USER_NOT_FOUND.getMessage()
            )
            every { userRepository.existsById(req.userId!!) } returns false
            every { orderRepository.save(orderData) } returns orderData
            // When
            val actualException = Assertions.assertThrows(CommonException::class.java) {
                orderService.createOrder(req)
            }
            // Then
            Assertions.assertEquals(expectedException, actualException)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 0) { orderRepository.save(orderData) }
        }
    }

    @Nested
    @DisplayName("updateOrders()")
    inner class UpdateOrderTest{
        private val req = OrderUpdateByIdReq(
            id = orderId,
            name = "new order name",
            price = 999.0,
            address = "address at test street",
            orderDateTime = LocalDate.now(),
            orderStatus = PROCESSING,
            userId = userId
        )
        private val orderData = Orders(
            name = "new order name",
            price = 999.0,
            address = "address at test street",
            orderDateTime = LocalDate.now(),
            orderStatus = PROCESSING,
            userId = userId
        )
        private val expectedResult = Orders(
            name = "new order name",
            price = 999.0,
            address = "address at test street",
            orderDateTime = LocalDate.now(),
            orderStatus = PROCESSING,
            userId = userId
        )
        @Test
        fun `GIVEN order WHEN updateOrder THEN success`() {
            // Given
            every { userRepository.existsById(req.userId!!) } returns true
            every { orderRepository.existsById(req.id!!) } returns true
            every { orderRepository.findById(req.id!!) } returns Optional.of(orderData)
            every { orderRepository.save(orderData) } returns orderData
            // When
            val actualResult = orderService.updateOrderById(req)
            // Then
            Assertions.assertEquals(expectedResult, actualResult)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 1) { orderRepository.existsById(req.id!!) }
            verify(exactly = 1) { orderRepository.findById(req.id!!) }
            verify(exactly = 1) { orderRepository.save(orderData) }
        }

        @Test
        fun `GIVEN invalid orderId WHEN updateOrder THEN throw`() {
            // Given
            val expectedException = CommonException(
                GET_ORDER_NOT_FOUND.getCode(),
                GET_ORDER_NOT_FOUND.getMessage()
            )
            every { userRepository.existsById(req.userId!!) } returns true
            every { orderRepository.existsById(req.id!!) } returns false
            every { orderRepository.findById(req.id!!) } returns Optional.of(orderData)
            every { orderRepository.deleteById(req.id!!) } just runs
            // When
            val actualException = Assertions.assertThrows(CommonException::class.java) {
                orderService.updateOrderById(req)
            }

            // Then
            Assertions.assertEquals(expectedException, actualException)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 1) { orderRepository.existsById(req.id!!) }
            verify(exactly = 0) { orderRepository.findById(req.id!!) }
            verify(exactly = 0) { orderRepository.deleteById(req.id!!) }
        }

        @Test
        fun `GIVEN invalid userId WHEN updateOrder THEN throw`() {
            // Given
            val expectedException = CommonException(
                GET_USER_NOT_FOUND.getCode(),
                GET_USER_NOT_FOUND.getMessage()
            )
            every { userRepository.existsById(req.userId!!) } returns false
            every { orderRepository.existsById(req.id!!) } returns true
            every { orderRepository.findById(req.id!!) } returns Optional.of(orderData)
            every { orderRepository.deleteById(req.id!!) } just runs
            // When
            val actualException = Assertions.assertThrows(CommonException::class.java) {
                orderService.updateOrderById(req)
            }

            // Then
            Assertions.assertEquals(expectedException, actualException)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 0) { orderRepository.existsById(req.id!!) }
            verify(exactly = 0) { orderRepository.findById(req.id!!) }
            verify(exactly = 0) { orderRepository.deleteById(req.id!!) }
        }
    }

    @Nested
    @DisplayName("deleteOrders()")
    inner class DeleteOrderTest{
        private val req = OrderDeleteByIdReq(
            id = orderId,
            userId = userId
        )
        private val orderData = Orders(
            name = "new order name",
            price = 999.0,
            address = "address at test street",
            orderDateTime = LocalDate.now(),
            orderStatus = PROCESSING,
            userId = userId
        )
        private val expectedResult = Orders(
            name = "new order name",
            price = 999.0,
            address = "address at test street",
            orderDateTime = LocalDate.now(),
            orderStatus = PROCESSING,
            userId = userId
        )

        @Test
        fun `GIVEN order WHEN deleteOrder THEN success`() {
            // Given
            every { userRepository.existsById(req.userId!!) } returns true
            every { orderRepository.existsById(req.id!!) } returns true
            every { orderRepository.findById(req.id!!) } returns Optional.of(orderData)
            every { orderRepository.deleteById(req.id!!) } just runs
            // When
            val actualResult = orderService.deleteOrderById(req)

            // Then
            Assertions.assertEquals(expectedResult, actualResult)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 1) { orderRepository.existsById(req.id!!) }
            verify(exactly = 1) { orderRepository.findById(req.id!!) }
            verify(exactly = 1) { orderRepository.deleteById(req.id!!) }
        }

        @Test
        fun `GIVEN invalid orderId WHEN deleteOrder THEN throw`() {
            // Given
            val expectedException = CommonException(
                GET_ORDER_NOT_FOUND.getCode(),
                GET_ORDER_NOT_FOUND.getMessage()
            )
            every { userRepository.existsById(req.userId!!) } returns true
            every { orderRepository.existsById(req.id!!) } returns false
            every { orderRepository.findById(req.id!!) } returns Optional.of(orderData)
            every { orderRepository.deleteById(req.id!!) } just runs
            // When
            val actualException = Assertions.assertThrows(CommonException::class.java) {
                orderService.deleteOrderById(req)
            }

            // Then
            Assertions.assertEquals(expectedException, actualException)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 1) { orderRepository.existsById(req.id!!) }
            verify(exactly = 0) { orderRepository.findById(req.id!!) }
            verify(exactly = 0) { orderRepository.deleteById(req.id!!) }
        }

        @Test
        fun `GIVEN invalid userId WHEN deleteOrder THEN throw`() {
            // Given
            val expectedException = CommonException(
                GET_USER_NOT_FOUND.getCode(),
                GET_USER_NOT_FOUND.getMessage()
            )
            every { userRepository.existsById(req.userId!!) } returns false
            every { orderRepository.existsById(req.id!!) } returns true
            every { orderRepository.findById(req.id!!) } returns Optional.of(orderData)
            every { orderRepository.deleteById(req.id!!) } just runs
            // When
            val actualException = Assertions.assertThrows(CommonException::class.java) {
                orderService.deleteOrderById(req)
            }

            // Then
            Assertions.assertEquals(expectedException, actualException)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 0) { orderRepository.existsById(req.id!!) }
            verify(exactly = 0) { orderRepository.findById(req.id!!) }
            verify(exactly = 0) { orderRepository.deleteById(req.id!!) }
        }
    }
}