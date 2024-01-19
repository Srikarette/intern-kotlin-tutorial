package com.example.tutorial.controller

import com.example.tutorial.TutorialApplication
import com.example.tutorial.config.dto.TemplateResponse
import com.example.tutorial.dto.enums.UserGender.OTHERS
import com.example.tutorial.entity.User
import com.example.tutorial.mapper.UserListGetResMapper
import com.example.tutorial.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.UUID

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TutorialApplication::class])
@AutoConfigureMockMvc
class UserControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
) {
    @MockkBean
    private lateinit var userService: UserService

    @Nested
    @DisplayName("getUserList()")
    inner class GetUserListTest {
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
                MockMvcRequestBuilders.get("/users/")
                    .contentType(APPLICATION_JSON),
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