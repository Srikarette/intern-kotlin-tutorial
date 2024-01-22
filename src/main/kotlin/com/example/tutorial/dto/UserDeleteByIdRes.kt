package com.example.tutorial.dto

import com.example.tutorial.dto.enums.UserGender
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class UserDeleteByIdRes(
    @field:NotNull(message = "userId must not be null")
    val id: UUID,
    @field:Size(max = 50, message = "firstName must no longer than 50 word")
    val firstName: String,
    @field:Size(max = 50, message = "lastname must no longer than 50 word")
    val lastName: String?,
    @field:NotNull(message = "email must not be null")
    @field:Size(max = 255, message = "email must no longer than 255 word")
    val email: String?,
    @field:Size(max = 255, message = "address must no longer than 255 word")
    val address: String?,
    val gender: UserGender?
)
