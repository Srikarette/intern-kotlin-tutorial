package com.example.tutorial.service

import com.example.tutorial.config.dto.exception.CommonException
import com.example.tutorial.constant.ResponseConstants.GET_USER_NOT_FOUND
import com.example.tutorial.dto.UserCreateReq
import com.example.tutorial.dto.UserDeleteByIdReq
import com.example.tutorial.dto.UserGetByIdReq
import com.example.tutorial.dto.UserGetOrdersReq
import com.example.tutorial.dto.UserUpdateByIdReq
import com.example.tutorial.dto.enums.OrderStatus.PROCESSING
import com.example.tutorial.dto.enums.UserGender.FEMALE
import com.example.tutorial.dto.enums.UserGender.MALE
import com.example.tutorial.dto.enums.UserGender.OTHERS
import com.example.tutorial.entity.User
import com.example.tutorial.entity.viewmodel.UserFullNameGetListProjection
import com.example.tutorial.entity.viewmodel.UserOrdersGetProjection
import com.example.tutorial.repository.UserRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.Optional
import java.util.UUID

class UserServiceTest {
    private val userRepository = mockk<UserRepository>()
    private val userService = spyk(UserService(userRepository))

    private val userId = UUID.randomUUID()

    @Nested
    @DisplayName("getUsers()")
    inner class GetUserTest {
        private val req = UserGetByIdReq(
            userId = userId
        )
        private val users = listOf(
            User(
                id = userId,
                firstName = "FirstName 1",
                lastName = "LastName 1",
                email = null,
                address = null,
                gender = OTHERS
            )
        )
        private val expectedResult = User(
            id = userId,
            firstName = "Aurora Sears",
            lastName = "Eddy Burt",
            email = null,
            address = null,
            gender = OTHERS,
        )
        private val expectedFullNameProjectionList =
            listOf(
                object : UserFullNameGetListProjection {
                    override val id: UUID = userId
                    override val fullName: String = "Alan wake"
                },
            )

        private val expectedUserOrderProjectionList =
            listOf(
                object : UserOrdersGetProjection {
                    override val id: UUID = userId
                    override val fullName: String = "Alan wake"
                    override val totalOrder: Int = 3
                    override val orderNumber: Int = 1
                    override val orderName: String = "test order 1"
                    override val orderPrice: Double = 355.5
                    override val orderAddress: String = "address at test street"
                    override val orderDateTime = "2024-01-22 +07"
                    override val orderStatus = PROCESSING
                },
            )

        private val accountData = User(
            id = userId,
            firstName = "Aurora Sears",
            lastName = "Eddy Burt",
            email = null,
            address = null,
            gender = OTHERS,
        )

        @Test
        fun `GIVEN users WHEN getUserList THEN success`() {
            // Given
            every { userRepository.findAll() } returns users

            // When
            val actualResult = userService.getUsers()

            // Then
            assertEquals(users, actualResult)
            verify(exactly = 1) { userRepository.findAll() }
        }

        @Test
        fun `GIVEN users WHEN getUsersFullName THEN success`() {
            // Given
            every { userRepository.getAllUsersFullName() } returns expectedFullNameProjectionList

            // When
            val actualResult = userService.getUsersFullName()

            // Then
            assertEquals(expectedFullNameProjectionList, actualResult)
            verify(exactly = 1) { userRepository.getAllUsersFullName() }
        }

        @Test
        fun `GIVEN users WHEN getUsersOrderName THEN success`() {
            // Given
            val req = UserGetOrdersReq(
                userId = userId
            )
            every { userRepository.existsById(req.userId!!) } returns true
            every { userRepository.getUserOrders(req.userId!!) } returns expectedUserOrderProjectionList

            // When
            val actualResult = userService.getUserOrderById(req)

            // Then
            assertEquals(expectedUserOrderProjectionList, actualResult)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 1) { userRepository.getUserOrders(req.userId!!) }
        }

        @Test
        fun `GIVEN user WHEN getUserById THEN success`() {
            // Given
            every { userRepository.existsById(req.userId!!) } returns true
            every { userRepository.findById(req.userId!!) } returns Optional.of(accountData)
            // When
            val actualResult = userService.getUserById(req)

            // Then
            assertEquals(expectedResult, actualResult)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 1) { userRepository.findById(req.userId!!) }
        }

        @Test
        fun `GIVEN invalid user WHEN getUserById THEN throw`() {
            // Given
            val expectedException = CommonException(
                GET_USER_NOT_FOUND.getCode(),
                GET_USER_NOT_FOUND.getMessage()
            )
            every { userRepository.existsById(req.userId!!) } returns false
            every { userRepository.findById(req.userId!!) } returns Optional.of(accountData)
            // When
            val actualException = assertThrows(CommonException::class.java) {
                userService.getUserById(req)
            }

            // Then
            assertEquals(expectedException, actualException)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 0) { userRepository.findById(req.userId!!) }
        }
    }

    @Nested
    @DisplayName("createUsers()")
    inner class CreateUserTest {
        private val req = UserCreateReq(
            firstName = "Alan",
            lastName = "Wake",
            email = "test@test.test",
            address = "Test address",
            gender = FEMALE
        )
        private val expectedResult = User(
            firstName = "Alan",
            lastName = "Wake",
            email = "test@test.test",
            address = "Test address",
            gender = FEMALE
        )

        private val accountData = User(
            firstName = "Alan",
            lastName = "Wake",
            email = "test@test.test",
            address = "Test address",
            gender = FEMALE
        )

        @Test
        fun `GIVEN user WHEN createUser THEN success`() {
            // Given
            every { userRepository.save(accountData) } returns accountData
            // When
            val actualResult = userService.createUser(req)

            // Then
            assertEquals(expectedResult, actualResult)
            verify(exactly = 1) { userRepository.save(accountData) }
        }
    }

    @Nested
    @DisplayName("updateUsers()")
    inner class UpdateUserTest {
        private val req = UserUpdateByIdReq(
            userId = userId,
            firstName = "newFirstName",
            lastName = "newLastName",
            email = "newEmail@test.test",
            address = "new test address",
            gender = MALE
        )
        private val expectedResult = User(
            firstName = "newFirstName",
            lastName = "newLastName",
            email = "newEmail@test.test",
            address = "new test address",
            gender = MALE
        )
        private val accountData = User(
            firstName = "newFirstName",
            lastName = "newLastName",
            email = "newEmail@test.test",
            address = "new test address",
            gender = MALE
        )

        @Test
        fun `GIVEN user WHEN updateUser THEN success`() {
            // Given
            every { userRepository.existsById(req.userId!!) } returns true
            every { userRepository.findById(req.userId!!) } returns Optional.of(accountData)
            every { userRepository.save(accountData) } returns accountData
            // When
            val actualResult = userService.updateUserById(req)
            // Then
            assertEquals(expectedResult, actualResult)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 1) { userRepository.findById(req.userId!!) }
            verify(exactly = 1) { userRepository.save(accountData) }
        }

        @Test
        fun `GIVEN invalid user WHEN updateUser THEN throw`() {
            // Given
            val expectedException = CommonException(
                GET_USER_NOT_FOUND.getCode(),
                GET_USER_NOT_FOUND.getMessage()
            )
            every { userRepository.existsById(req.userId!!) } returns false
            // When
            val actualException = assertThrows(CommonException::class.java) {
                userService.updateUserById(req)
            }

            // Then
            assertEquals(expectedException, actualException)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 0) { userRepository.findById(req.userId!!) }
            verify(exactly = 0) { userRepository.save(accountData) }
        }
    }

    @Nested
    @DisplayName("deleteUsers()")
    inner class DeleteUserTest {
        private val req = UserDeleteByIdReq(
            userId = userId,
            email = "test@test.test"
        )
        private val expectedResult = User(
            firstName = "newFirstName",
            lastName = "newLastName",
            email = "newEmail@test.test",
            address = "new test address",
            gender = MALE
        )

        private val accountData = User(
            firstName = "newFirstName",
            lastName = "newLastName",
            email = "newEmail@test.test",
            address = "new test address",
            gender = MALE
        )

        @Test
        fun `GIVEN user WHEN deleteUser THEN success`() {
            // Given
            every { userRepository.existsById(req.userId!!) } returns true
            every { userRepository.findById(req.userId!!) } returns Optional.of(accountData)
            every { userRepository.deleteById(req.userId!!) } just runs
            // When
            val actualResult = userService.deleteUserById(req)

            // Then
            assertEquals(expectedResult, actualResult)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 1) { userRepository.findById(req.userId!!) }
            verify(exactly = 1) { userRepository.deleteById(req.userId!!) }
        }

        @Test
        fun `GIVEN invalid user WHEN deleteUser THEN throw`() {
            // Given
            val expectedException = CommonException(
                GET_USER_NOT_FOUND.getCode(),
                GET_USER_NOT_FOUND.getMessage()
            )
            every { userRepository.existsById(req.userId!!) } returns false
            // When
            val actualException = assertThrows(CommonException::class.java) {
                userService.deleteUserById(req)
            }

            // Then
            assertEquals(expectedException, actualException)
            verify(exactly = 1) { userRepository.existsById(req.userId!!) }
            verify(exactly = 0) { userRepository.findById(req.userId!!) }
            verify(exactly = 0) { userRepository.deleteById(req.userId!!) }
        }
    }
}
