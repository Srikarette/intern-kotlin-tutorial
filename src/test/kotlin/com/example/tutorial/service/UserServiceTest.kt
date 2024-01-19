package com.example.tutorial.service

import com.example.tutorial.dto.enums.UserGender.OTHERS
import com.example.tutorial.entity.User
import com.example.tutorial.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.UUID

class UserServiceTest {
    private val userRepository = mockk<UserRepository>()
    private val userService = spyk(UserService(userRepository))

    @Nested
    @DisplayName("getUsers()")
    inner class GetUserTest {
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
            // Given
            every { userRepository.findAll() } returns users

            // When
            val actualResult = userService.getUsers()

            // Then
            assertEquals(users, actualResult)
            verify(exactly = 1) { userRepository.findAll() }
        }
    }
}