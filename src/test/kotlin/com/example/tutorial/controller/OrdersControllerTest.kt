package com.example.tutorial.controller

import com.example.tutorial.TutorialApplication
import com.example.tutorial.config.constant.AppConfigConstants
import com.example.tutorial.config.dto.TemplateResponse
import com.example.tutorial.dto.OrderCreateReq
import com.example.tutorial.dto.OrderDeleteByIdReq
import com.example.tutorial.dto.OrderGetByIdReq
import com.example.tutorial.dto.OrderUpdateByIdReq
import com.example.tutorial.dto.enums.OrderStatus.PENDING
import com.example.tutorial.entity.Orders
import com.example.tutorial.mapper.OrderCreateResMapper
import com.example.tutorial.mapper.OrderDeleteByIdResMapper
import com.example.tutorial.mapper.OrderGetByIdResMapper
import com.example.tutorial.mapper.OrderListGetResMapper
import com.example.tutorial.mapper.OrderUpdateByIdResMapper
import com.example.tutorial.service.OrderService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TutorialApplication::class])
@AutoConfigureMockMvc
class OrdersControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
) {
    @MockkBean
    private lateinit var orderService: OrderService

    companion object {
        private fun generateRandomString(length: Int): String {
            val charPool: List<Char> =
                ('a'..'z') + ('A'..'Z') + ('0'..'9')
            return (1..length)
                .map { charPool[Random.nextInt(0, charPool.size)] }
                .joinToString("")
        }

        @JvmStatic
        fun provideInvalidOrderCreate(): List<Arguments> {
            val userId = UUID.randomUUID()
            val invalidName = generateRandomString(51)
            val invalidInfo = generateRandomString(256)
            return listOf(
                Arguments.of(
                    "name is null",
                    OrderCreateReq(
                        name = null,
                        price = 355.25,
                        address = "test address",
                        orderDateTime = LocalDate.now(),
                        orderStatus = PENDING,
                        userId = userId
                    ),
                    listOf(
                        "order name must not be null",
                    )
                ),
                Arguments.of(
                    "name is exceed 50 limit",
                    OrderCreateReq(
                        name = invalidName,
                        price = 355.25,
                        address = "test address",
                        orderDateTime = LocalDate.now(),
                        orderStatus = PENDING,
                        userId = userId
                    ),
                    listOf(
                        "order name must no longer than 50 word",
                    )
                ),
                Arguments.of(
                    "price is null",
                    OrderCreateReq(
                        name = "test order 1",
                        price = null,
                        address = "test address",
                        orderDateTime = LocalDate.now(),
                        orderStatus = PENDING,
                        userId = userId
                    ),
                    listOf(
                        "order price must not be null",
                    )
                ),
                Arguments.of(
                    "address is exceed 255 word",
                    OrderCreateReq(
                        name = "test order 1",
                        price = 355.25,
                        address = invalidInfo,
                        orderDateTime = LocalDate.now(),
                        orderStatus = PENDING,
                        userId = userId
                    ),
                    listOf(
                        "order address must no longer than 255 word",
                    )
                ),
                Arguments.of(
                    "price is exceed limit",
                    OrderCreateReq(
                        name = "test order 1",
                        price = 9999999.999,
                        address = "test address",
                        orderDateTime = LocalDate.now(),
                        orderStatus = PENDING,
                        userId = userId
                    ),
                    listOf(
                        "order price must not exceed 999,999",
                    )
                ),
                Arguments.of(
                    "userId is null",
                    OrderCreateReq(
                        name = "test order 1",
                        price = 355.25,
                        address = "test address",
                        orderDateTime = LocalDate.now(),
                        orderStatus = PENDING,
                        userId = null
                    ),
                    listOf(
                        "order by must not be null",
                    )
                ),
                Arguments.of(
                    "everything is null",
                    OrderCreateReq(
                        name = null,
                        price = null,
                        address = null,
                        orderDateTime = null,
                        orderStatus = null,
                        userId = null
                    ),
                    listOf(
                        "order name must not be null",
                        "order price must not be null",
                        "order by must not be null"
                    )
                ),
                Arguments.of(
                    "everything is wrong",
                    OrderCreateReq(
                        name = invalidName,
                        price = 9999999.999,
                        address = invalidInfo,
                        orderDateTime = null,
                        orderStatus = null,
                        userId = null
                    ),
                    listOf(
                        "order name must no longer than 50 word",
                        "order price must not exceed 999,999",
                        "order address must no longer than 255 word",
                        "order by must not be null"
                    )
                ),
            )
        }

        @JvmStatic
        fun provideInvalidOrderUpdate(): List<Arguments> {
            val userId = UUID.randomUUID()
            val orderId = UUID.randomUUID()
            val invalidName = generateRandomString(51)
            val invalidInfo = generateRandomString(256)
            return listOf(
                Arguments.of(
                    "orderId is null",
                    OrderUpdateByIdReq(
                        id = null,
                        name = null,
                        price = 355.25,
                        address = "test address",
                        orderDateTime = LocalDate.now(),
                        orderStatus = PENDING,
                        userId = userId
                    ),
                    listOf(
                        "orderId must not be null",
                    )
                ),
                Arguments.of(
                    "userId is null",
                    OrderUpdateByIdReq(
                        id = orderId,
                        name = "test order 1",
                        price = 355.25,
                        address = "test address",
                        orderDateTime = LocalDate.now(),
                        orderStatus = PENDING,
                        userId = null
                    ),
                    listOf(
                        "order by must not be null",
                    )
                ),
                Arguments.of(
                    "name is exceed 50 word",
                    OrderUpdateByIdReq(
                        id = orderId,
                        name = invalidName,
                        price = null,
                        address = "test address",
                        orderDateTime = LocalDate.now(),
                        orderStatus = PENDING,
                        userId = userId
                    ),
                    listOf(
                        "order name must no longer than 50 word",
                    )
                ),
                Arguments.of(
                    "price is exceed 999999 word",
                    OrderUpdateByIdReq(
                        id = orderId,
                        name = "test order 1",
                        price = 9999999.999,
                        address = "test address",
                        orderDateTime = LocalDate.now(),
                        orderStatus = PENDING,
                        userId = userId
                    ),
                    listOf(
                        "order price must not exceed 999,999",
                    )
                ),
                Arguments.of(
                    "address is exceed 255 word",
                    OrderUpdateByIdReq(
                        id = orderId,
                        name = "test order 1",
                        price = 355.25,
                        address = invalidInfo,
                        orderDateTime = LocalDate.now(),
                        orderStatus = PENDING,
                        userId = userId
                    ),
                    listOf(
                        "order address must no longer than 255 word",
                    )
                ),
                Arguments.of(
                    "everything is null",
                    OrderUpdateByIdReq(
                        id = null,
                        name = null,
                        price = null,
                        address = null,
                        orderDateTime = null,
                        orderStatus = null,
                        userId = null
                    ),
                    listOf(
                        "orderId must not be null",
                        "order by must not be null",
                    )
                ),
                Arguments.of(
                    "everything is wrong",
                    OrderUpdateByIdReq(
                        id = null,
                        name = invalidName,
                        price = 9999999.999,
                        address = invalidInfo,
                        orderDateTime = null,
                        orderStatus = null,
                        userId = null
                    ),
                    listOf(
                        "orderId must not be null",
                        "order name must no longer than 50 word",
                        "order price must not exceed 999,999",
                        "order address must no longer than 255 word",
                        "order by must not be null"
                    )
                ),
            )
        }

        @JvmStatic
        fun provideInvalidOrderDelete(): List<Arguments> {
            val userId = UUID.randomUUID()
            val orderId = UUID.randomUUID()
            return listOf(
                Arguments.of(
                    "orderId is null",
                    OrderDeleteByIdReq(
                        id = null,
                        userId = userId
                    ),
                    listOf(
                        "orderId must not be null",
                    )
                ),
                Arguments.of(
                    "userId is null",
                    OrderDeleteByIdReq(
                        id = orderId,
                        userId = null
                    ),
                    listOf(
                        "order by must not be null",
                    )
                ),
                Arguments.of(
                    "everything is null",
                    OrderDeleteByIdReq(
                        id = null,
                        userId = null
                    ),
                    listOf(
                        "order by must not be null",
                        "orderId must not be null",
                    )
                ),
            )
        }
    }

    @Nested
    @DisplayName("getOrderList()")
    inner class GetOrderListTest {
        private val orders = listOf(
            Orders(
                id = UUID.randomUUID(),
                name = "test order 1",
                price = 330.25,
                address = "test address",
                orderDateTime = LocalDate.now(),
                orderStatus = PENDING,
                userId = UUID.randomUUID()
            )
        )
        private val orderId = UUID.randomUUID()

        @Test
        fun `GIVEN users WHEN getUserList THEN success`() {

            every { orderService.getOrders() } returns orders

            val res = OrderListGetResMapper.toOrderListGetRes(orders)

            // When
            mockMvc.perform(
                MockMvcRequestBuilders.post("/orders/")
                    .contentType(MediaType.APPLICATION_JSON),
            )
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(TemplateResponse(res))),
                )
        }

        @Test
        fun `GIVEN user WHEN getOrderById THEN success`() {
            // Given
            val order = Orders(
                id = UUID.randomUUID(),
                name = "test order 1",
                price = 330.25,
                address = "test address",
                orderDateTime = LocalDate.now(),
                orderStatus = PENDING,
                userId = UUID.randomUUID()
            )
            val req = OrderGetByIdReq(
                id = orderId
            )
            every { orderService.getOrderById(req) } returns order

            val res = OrderGetByIdResMapper.toOrderGetByIdRes(order)

            // When
            mockMvc.perform(
                MockMvcRequestBuilders.post("/orders/get")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(TemplateResponse(res))),
                )
        }

        @Test
        fun `GIVEN null WHEN getUserById THEN throw`() {
            // Given
            val req = OrderGetByIdReq(
                id = null
            )
            val errorMessage = listOf("orderId must not be null")

            val expectedException =
                TemplateResponse(
                    AppConfigConstants.ERR_UNABLE_TO_PROCEED_CODE,
                    AppConfigConstants.ERR_UNABLE_TO_PROCEED_MESSAGE,
                    errorMessage
                )
            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/orders/get")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedException))
                )
        }
    }

    @Nested
    @DisplayName("createOrder()")
    inner class CreateOrder {
        @ParameterizedTest(name = "GIVEN {0} WHEN createOrder THEN {2}")
        @MethodSource("com.example.tutorial.controller.OrdersControllerTest#provideInvalidOrderCreate")
        fun `Given invalid request When createOrder Then validation error`(
            testCase: String,
            req: OrderCreateReq,
            errorResponseList: List<String>
        ) {
            // Given
            val expectedException =
                TemplateResponse(
                    AppConfigConstants.ERR_UNABLE_TO_PROCEED_CODE,
                    AppConfigConstants.ERR_UNABLE_TO_PROCEED_MESSAGE,
                    errorResponseList
                )
            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/orders/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedException))
                )
        }

        @Test
        fun `GIVEN order WHEN createOrder THEN success`() {
            // Given
            val req = OrderCreateReq(
                name = "test order 1",
                price = 355.25,
                address = "test address",
                orderDateTime = LocalDate.now(),
                orderStatus = PENDING,
                userId = UUID.randomUUID()

            )
            val order = Orders(
                id = UUID.randomUUID(),
                name = "test order 1",
                price = 330.25,
                address = "test address",
                orderDateTime = LocalDate.now(),
                orderStatus = PENDING,
                userId = UUID.randomUUID()
            )
            every { orderService.createOrder(req) } returns order

            val res = OrderCreateResMapper.toOrderCreateRes(order)

            // When
            mockMvc.perform(
                MockMvcRequestBuilders.post("/orders/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(TemplateResponse(res))),
                )
        }
    }

    @Nested
    @DisplayName("updateOrder()")
    inner class UpdateOrderTest {
        @ParameterizedTest(name = "GIVEN {0} WHEN createOrder THEN {2}")
        @MethodSource("com.example.tutorial.controller.OrdersControllerTest#provideInvalidOrderUpdate")
        fun `Given invalid request When createOrder Then validation error`(
            testCase: String,
            req: OrderUpdateByIdReq,
            errorResponseList: List<String>
        ) {
            // Given
            val expectedException =
                TemplateResponse(
                    AppConfigConstants.ERR_UNABLE_TO_PROCEED_CODE,
                    AppConfigConstants.ERR_UNABLE_TO_PROCEED_MESSAGE,
                    errorResponseList
                )
            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/orders/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedException))
                )
        }

        @Test
        fun `GIVEN user WHEN updateUser THEN success`() {
            val userId = UUID.randomUUID()
            val orderId = UUID.randomUUID()
            // Given
            val req = OrderUpdateByIdReq(
                id = orderId,
                name = "test order 1",
                price = 355.25,
                address = "test address",
                orderDateTime = LocalDate.now(),
                orderStatus = PENDING,
                userId = userId
            )
            val order = Orders(
                id = orderId,
                name = "test order 1",
                price = 330.25,
                address = "test address",
                orderDateTime = LocalDate.now(),
                orderStatus = PENDING,
                userId = userId
            )
            every { orderService.updateOrderById(req) } returns order

            val res = OrderUpdateByIdResMapper.toOrderUpdateByIdRes(order)

            // When
            mockMvc.perform(
                MockMvcRequestBuilders.post("/orders/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(TemplateResponse(res))),
                )
        }
    }

    @Nested
    @DisplayName("deleteOrder()")
    inner class DeleteOrderTest {
        @ParameterizedTest(name = "GIVEN {0} WHEN deleteOrder THEN {2}")
        @MethodSource("com.example.tutorial.controller.OrdersControllerTest#provideInvalidOrderDelete")
        fun `Given invalid request When deleteUser Then validation error`(
            testCase: String,
            req: OrderDeleteByIdReq,
            errorResponseList: List<String>
        ) {
            // Given
            val expectedException =
                TemplateResponse(
                    AppConfigConstants.ERR_UNABLE_TO_PROCEED_CODE,
                    AppConfigConstants.ERR_UNABLE_TO_PROCEED_MESSAGE,
                    errorResponseList
                )
            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/orders/delete")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedException))
                )
        }

        @Test
        fun `GIVEN user WHEN deleteUser THEN success`() {
            val userId = UUID.randomUUID()
            val orderId = UUID.randomUUID()
            // Given
            val req = OrderDeleteByIdReq(
                userId = userId,
                id = orderId
            )
            val order = Orders(
                id = orderId,
                name = "test order 1",
                price = 330.25,
                address = "test address",
                orderDateTime = LocalDate.now(),
                orderStatus = PENDING,
                userId = userId
            )
            every { orderService.deleteOrderById(req) } returns order

            val res = OrderDeleteByIdResMapper.toOrderDeleteByIdRes(order)

            // When
            mockMvc.perform(
                MockMvcRequestBuilders.post("/orders/delete")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(TemplateResponse(res))),
                )
        }
    }
}