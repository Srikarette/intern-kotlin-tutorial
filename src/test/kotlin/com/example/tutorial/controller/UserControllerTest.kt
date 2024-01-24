package com.example.tutorial.controller

import com.example.tutorial.TutorialApplication
import com.example.tutorial.config.constant.AppConfigConstants.Companion.ERR_UNABLE_TO_PROCEED_CODE
import com.example.tutorial.config.constant.AppConfigConstants.Companion.ERR_UNABLE_TO_PROCEED_MESSAGE
import com.example.tutorial.config.dto.TemplateResponse
import com.example.tutorial.dto.UserCreateReq
import com.example.tutorial.dto.UserDeleteByIdReq
import com.example.tutorial.dto.UserGetByIdReq
import com.example.tutorial.dto.UserGetOrdersReq
import com.example.tutorial.dto.UserUpdateByIdReq
import com.example.tutorial.dto.enums.OrderStatus
import com.example.tutorial.dto.enums.UserGender.FEMALE
import com.example.tutorial.dto.enums.UserGender.MALE
import com.example.tutorial.dto.enums.UserGender.OTHERS
import com.example.tutorial.entity.User
import com.example.tutorial.entity.viewmodel.UserFullNameGetListProjection
import com.example.tutorial.entity.viewmodel.UserOrdersGetProjection
import com.example.tutorial.mapper.UserCreateResMapper
import com.example.tutorial.mapper.UserGetByIdResMapper
import com.example.tutorial.mapper.UserGetOrdersResMapper
import com.example.tutorial.mapper.UserListGetFullNameResMapper
import com.example.tutorial.mapper.UserListGetResMapper
import com.example.tutorial.service.UserService
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
import org.springframework.http.MediaType.APPLICATION_JSON
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
class UserControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
) {
    @MockkBean
    private lateinit var userService: UserService

    companion object {
        private fun generateRandomString(length: Int): String {
            val charPool: List<Char> =
                ('a'..'z') + ('A'..'Z') + ('0'..'9') // You can customize the character pool as needed
            return (1..length)
                .map { charPool[Random.nextInt(0, charPool.size)] }
                .joinToString("")
        }

        // parameters for user create
        @JvmStatic
        fun provideInvalidUserCreate(): List<Arguments> {
            val inValidName = generateRandomString(51)
            val inValidInfo = generateRandomString(256)
            return listOf(
                Arguments.of(
                    "firstName is null",
                    UserCreateReq(
                        firstName = null,
                        lastName = "wake",
                        email = "test@test.test",
                        address = "address at test street",
                        gender = MALE
                    ),
                    listOf(
                        "firstName must not be null",
                    )
                ),
                Arguments.of(
                    "lastName is null",
                    UserCreateReq(
                        firstName = "alan",
                        lastName = null,
                        email = "test@test.test",
                        address = "address at test street",
                        gender = MALE
                    ),
                    listOf(
                        "lastName must not be null",
                    )
                ),
                Arguments.of(
                    "gender is null",
                    UserCreateReq(
                        firstName = "alan",
                        lastName = "wake",
                        email = "test@test.test",
                        address = "address at test street",
                        gender = null
                    ),
                    listOf(
                        "gender must not be null",
                    )
                ),
                Arguments.of(
                    "firstName is exceed 50 limit",
                    UserCreateReq(
                        firstName = inValidName,
                        lastName = "wake",
                        email = "test@test.test",
                        address = "address at test street",
                        gender = MALE
                    ),
                    listOf(
                        "firstName must no longer than 50 word",
                    )
                ),
                Arguments.of(
                    "lastName is exceed 50 limit",
                    UserCreateReq(
                        firstName = "alan",
                        lastName = inValidName,
                        email = "test@test.test",
                        address = "address at test street",
                        gender = MALE
                    ),
                    listOf(
                        "lastname must no longer than 50 word",
                    )
                ),
                Arguments.of(
                    "email is exceed 255 limit",
                    UserCreateReq(
                        firstName = "alan",
                        lastName = "wake",
                        email = inValidInfo,
                        address = "address at test street",
                        gender = MALE
                    ),
                    listOf(
                        "email must no longer than 255 word",
                    )
                ),
                Arguments.of(
                    "address is exceed 255 limit",
                    UserCreateReq(
                        firstName = "alan",
                        lastName = "wake",
                        email = "test@test.test",
                        address = inValidInfo,
                        gender = MALE
                    ),
                    listOf(
                        "address must no longer than 255 word",
                    )
                ),
                Arguments.of(
                    "name is exceed 50 limit",
                    UserCreateReq(
                        firstName = inValidName,
                        lastName = inValidName,
                        email = "test@test.test",
                        address = "address at test street",
                        gender = MALE
                    ),
                    listOf(
                        "firstName must no longer than 50 word",
                        "lastname must no longer than 50 word",
                    )
                ),
                Arguments.of(
                    "everything is null",
                    UserCreateReq(
                        firstName = null,
                        lastName = null,
                        email = null,
                        address = null,
                        gender = null
                    ),
                    listOf(
                        "lastName must not be null",
                        "firstName must not be null",
                        "gender must not be null",
                    )
                ),
                Arguments.of(
                    "everything is wrong",
                    UserCreateReq(
                        firstName = inValidName,
                        lastName = inValidName,
                        email = inValidInfo,
                        address = inValidInfo,
                        gender = null
                    ),
                    listOf(
                        "firstName must no longer than 50 word",
                        "lastname must no longer than 50 word",
                        "gender must not be null",
                        "address must no longer than 255 word",
                        "email must no longer than 255 word",
                    )
                ),
            )
        }

        // parameters for user update
        @JvmStatic
        fun provideInvalidUserUpdate(): List<Arguments> {
            val inValidName = generateRandomString(51)
            val inValidInfo = generateRandomString(256)
            val userId = UUID.randomUUID()
            return listOf(
                Arguments.of(
                    "userId is null",
                    UserUpdateByIdReq(
                        userId = null,
                        firstName = "alan",
                        lastName = "wake",
                        email = "test@test.test",
                        address = "address at test street",
                        gender = MALE
                    ),
                    listOf(
                        "userId must not be null",
                    )
                ),
                Arguments.of(
                    "firstName is exceed 50 limit",
                    UserUpdateByIdReq(
                        userId = userId,
                        firstName = inValidName,
                        lastName = "wake",
                        email = "test@test.test",
                        address = "address at test street",
                        gender = MALE
                    ),
                    listOf(
                        "firstName must no longer than 50 word",
                    )
                ),
                Arguments.of(
                    "lastName is exceed 50 limit",
                    UserUpdateByIdReq(
                        userId = userId,
                        firstName = "alan",
                        lastName = inValidName,
                        email = "test@test.test",
                        address = "address at test street",
                        gender = MALE
                    ),
                    listOf(
                        "lastname must no longer than 50 word",
                    )
                ),
                Arguments.of(
                    "email is null",
                    UserUpdateByIdReq(
                        userId = userId,
                        firstName = "alan",
                        lastName = "wake",
                        email = null,
                        address = "address at test street",
                        gender = MALE
                    ),
                    listOf(
                        "email must not be null",
                    )
                ),
                Arguments.of(
                    "email is exceed 255 limit",
                    UserUpdateByIdReq(
                        userId = userId,
                        firstName = "alan",
                        lastName = "wake",
                        email = inValidInfo,
                        address = "address at test street",
                        gender = MALE
                    ),
                    listOf(
                        "email must no longer than 255 word",
                    )
                ),
                Arguments.of(
                    "address is exceed 255 limit",
                    UserUpdateByIdReq(
                        userId = userId,
                        firstName = "alan",
                        lastName = "wake",
                        email = "test@test.test",
                        address = inValidInfo,
                        gender = MALE
                    ),
                    listOf(
                        "address must no longer than 255 word",
                    )
                ),
                Arguments.of(
                    "name is exceed 50 limit",
                    UserUpdateByIdReq(
                        userId = userId,
                        firstName = inValidName,
                        lastName = inValidName,
                        email = "test@test.test",
                        address = "address at test street",
                        gender = MALE
                    ),
                    listOf(
                        "firstName must no longer than 50 word",
                        "lastname must no longer than 50 word",
                    )
                ),
                Arguments.of(
                    "everything is null",
                    UserUpdateByIdReq(
                        userId = null,
                        firstName = null,
                        lastName = null,
                        email = null,
                        address = null,
                        gender = null
                    ),
                    listOf(
                        "userId must not be null",
                        "email must not be null"
                    )
                ),
                Arguments.of(
                    "everything is wrong",
                    UserUpdateByIdReq(
                        userId = null,
                        firstName = inValidName,
                        lastName = inValidName,
                        email = inValidInfo,
                        address = inValidInfo,
                        gender = FEMALE
                    ),
                    listOf(
                        "userId must not be null",
                        "firstName must no longer than 50 word",
                        "lastname must no longer than 50 word",
                        "email must no longer than 255 word",
                        "address must no longer than 255 word"
                    )
                ),
            )
        }

        // parameters for user delete
        @JvmStatic
        fun provideInvalidUserDelete(): List<Arguments> {
            val inValidInfo = generateRandomString(256)
            val userId = UUID.randomUUID()
            return listOf(
                Arguments.of(
                    "userId is null",
                    UserDeleteByIdReq(
                        userId = null,
                        email = "test@test.test",
                    ),
                    listOf(
                        "userId must not be null",
                    )
                ),
                Arguments.of(
                    "email is null",
                    UserDeleteByIdReq(
                        userId = userId,
                        email = null,
                    ),
                    listOf(
                        "email must not be null",
                    )
                ),
                Arguments.of(
                    "email is exceed 255 limit",
                    UserDeleteByIdReq(
                        userId = userId,
                        email = inValidInfo,
                    ),
                    listOf(
                        "email must no longer than 255 word",
                    )
                ),
                Arguments.of(
                    "everything is null",
                    UserDeleteByIdReq(
                        userId = null,
                        email = null,
                    ),
                    listOf(
                        "userId must not be null",
                        "email must not be null",
                    )
                ),
            )
        }
    }

    @Nested
    @DisplayName("getUserList()")
    inner class GetUserListTest {
        private val userId = UUID.randomUUID()
        private val users = listOf(
            User(
                id = UUID.randomUUID(),
                firstName = "FirstName 1",
                lastName = "LastName 1",
                email = null,
                address = null,
                gender = OTHERS
            )
        )

        @Test
        fun `GIVEN users WHEN getUserList THEN success`() {

            every { userService.getUsers() } returns users

            val res = UserListGetResMapper.toUserListGetRes(users)

            // When
            mockMvc.perform(
                MockMvcRequestBuilders.post("/users/")
                    .contentType(APPLICATION_JSON),
            )
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(TemplateResponse(res))),
                )
        }

        @Test
        fun `GIVEN user WHEN getUserById THEN success`() {
            // Given
            val user = User(
                id = userId,
                firstName = "Alan",
                lastName = "Wake",
                email = "test@test.test",
                address = "Test address",
                gender = FEMALE
            )
            val req = UserGetByIdReq(
                userId = userId
            )
            every { userService.getUserById(req) } returns user

            val res = UserGetByIdResMapper.toUserGetByIdRes(user)

            // When
            mockMvc.perform(
                MockMvcRequestBuilders.post("/users/get")
                    .contentType(APPLICATION_JSON)
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
            val req = UserGetByIdReq(
                userId = null
            )
            val errorMessage = listOf("userId must not be null")

            val expectedException =
                TemplateResponse(
                    ERR_UNABLE_TO_PROCEED_CODE,
                    ERR_UNABLE_TO_PROCEED_MESSAGE,
                    errorMessage
                )
            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/users/get")
                    .contentType(APPLICATION_JSON)
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
    @DisplayName("createUser()")
    inner class CreateUserTest {
        @ParameterizedTest(name = "GIVEN {0} WHEN createPet THEN {2}")
        @MethodSource("com.example.tutorial.controller.UserControllerTest#provideInvalidUserCreate")
        fun `Given invalid request When createUser Then validation error`(
            testCase: String,
            req: UserCreateReq,
            errorResponseList: List<String>
        ) {
            // Given
            val expectedException =
                TemplateResponse(
                    ERR_UNABLE_TO_PROCEED_CODE,
                    ERR_UNABLE_TO_PROCEED_MESSAGE,
                    errorResponseList
                )
            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/users/create")
                    .contentType(APPLICATION_JSON)
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
        fun `GIVEN user WHEN createUser THEN success`() {
            // Given
            val req = UserCreateReq(
                firstName = "Alan",
                lastName = "Wake",
                email = "test@test.test",
                address = "Test address",
                gender = FEMALE
            )
            val user = User(
                id = UUID.randomUUID(),
                firstName = "Alan",
                lastName = "Wake",
                email = "test@test.test",
                address = "Test address",
                gender = FEMALE
            )
            every { userService.createUser(req) } returns user

            val res = UserCreateResMapper.toUserCreateRes(user)

            // When
            mockMvc.perform(
                MockMvcRequestBuilders.post("/users/create")
                    .contentType(APPLICATION_JSON)
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
    @DisplayName("updateUser()")
    inner class UpdateUser {
        @ParameterizedTest(name = "GIVEN {0} WHEN createPet THEN {2}")
        @MethodSource("com.example.tutorial.controller.UserControllerTest#provideInvalidUserUpdate")
        fun `Given invalid request When updateUser Then validation error`(
            testCase: String,
            req: UserUpdateByIdReq,
            errorResponseList: List<String>
        ) {
            // Given
            val expectedException =
                TemplateResponse(
                    ERR_UNABLE_TO_PROCEED_CODE,
                    ERR_UNABLE_TO_PROCEED_MESSAGE,
                    errorResponseList
                )
            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/users/update")
                    .contentType(APPLICATION_JSON)
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
            // Given
            val req = UserUpdateByIdReq(
                userId = userId,
                firstName = "new first name",
                lastName = "new last name",
                email = "new test@test.test",
                address = "new address",
                gender = OTHERS
            )
            val user = User(
                id = userId,
                firstName = "new first name",
                lastName = "new last name",
                email = "new test@test.test",
                address = "new address",
                gender = OTHERS
            )
            every { userService.updateUserById(req) } returns user

            val res = UserCreateResMapper.toUserCreateRes(user)

            // When
            mockMvc.perform(
                MockMvcRequestBuilders.post("/users/update")
                    .contentType(APPLICATION_JSON)
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
    @DisplayName("deleteUser()")
    inner class DeleteUserTest {
        @ParameterizedTest(name = "GIVEN {0} WHEN createPet THEN {2}")
        @MethodSource("com.example.tutorial.controller.UserControllerTest#provideInvalidUserDelete")
        fun `Given invalid request When updateUser Then validation error`(
            testCase: String,
            req: UserDeleteByIdReq,
            errorResponseList: List<String>
        ) {
            // Given
            val expectedException =
                TemplateResponse(
                    ERR_UNABLE_TO_PROCEED_CODE,
                    ERR_UNABLE_TO_PROCEED_MESSAGE,
                    errorResponseList
                )
            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/users/delete")
                    .contentType(APPLICATION_JSON)
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
            // Given
            val req = UserDeleteByIdReq(
                userId = userId,
                email = "new test@test.test",
            )
            val user = User(
                id = userId,
                firstName = "new first name",
                lastName = "new last name",
                email = "new test@test.test",
                address = "new address",
                gender = OTHERS
            )
            every { userService.deleteUserById(req) } returns user

            val res = UserCreateResMapper.toUserCreateRes(user)

            // When
            mockMvc.perform(
                MockMvcRequestBuilders.post("/users/delete")
                    .contentType(APPLICATION_JSON)
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
    @DisplayName("projectionGetList()")
    inner class ProjectionTest {
        @Test
        fun `GIVEN users WHEN getUserList THEN success`() {
            // Given
            val userId = UUID.randomUUID()
            val users =
                listOf(
                    object : UserFullNameGetListProjection {
                        override val id: UUID = userId
                        override val fullName: String = "Alan wake"
                    },
                )
            every { userService.getUsersFullName() } returns users

            val res = UserListGetFullNameResMapper.toUserListGetFullNameRes(users)

            // When
            mockMvc.perform(
                MockMvcRequestBuilders.post("/users/get/full/name")
                    .contentType(APPLICATION_JSON),
            )
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(TemplateResponse(res))),
                )
        }

        @Test
        fun `GIVEN user WHEN getUserOrder THEN success`() {
            // Given
            val userId = UUID.randomUUID()
            val user =
                listOf(
                    object : UserOrdersGetProjection {
                        override val id: UUID = userId
                        override val fullName: String = "Alan wake"
                        override val totalOrder: Int = 3
                        override val orderNumber: Int = 1
                        override val orderName: String = "test order 1"
                        override val orderPrice: Double = 355.5
                        override val orderAddress: String = "address at test street"
                        override val orderDateTime = LocalDate.now()
                        override val orderStatus = OrderStatus.PROCESSING
                    },
                )
            val req = UserGetOrdersReq(
                userId = userId
            )
            every { userService.getUserOrderById(req) } returns user

            val res = UserGetOrdersResMapper.toUserGetOrdersRes(user)

            // When
            mockMvc.perform(
                MockMvcRequestBuilders.post("/users/get/order")
                    .contentType(APPLICATION_JSON)
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
        fun `GIVEN null WHEN getUserOrder THEN throw`() {
            // Given
            val req = UserGetOrdersReq(
                userId = null
            )
            val errorMessage = listOf("userId must not be null")

            val expectedException =
                TemplateResponse(
                    ERR_UNABLE_TO_PROCEED_CODE,
                    ERR_UNABLE_TO_PROCEED_MESSAGE,
                    errorMessage
                )
            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/users/get/order")
                    .contentType(APPLICATION_JSON)
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
}