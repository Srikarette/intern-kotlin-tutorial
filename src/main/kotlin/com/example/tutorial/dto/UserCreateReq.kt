package com.example.tutorial.dto

import com.example.tutorial.dto.enums.UserGender
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UserCreateReq(
    @field:NotNull(message = "firstName must not be null")
    @field:Size(max = 50, message = "firstName must no longer than 50 word")
    val firstName: String,
    @field:NotNull(message = "lastName must not be null")
    @field:Size(max = 50, message = "lastname must no longer than 50 word")
    val lastName: String,
    @field:Size(max = 255, message = "email must no longer than 50 word")
    val email: String?,
    @field:Size(max = 255, message = "address must no longer than 50 word")
    val address: String?,
    @field:NotNull(message = "gender must not be null")
    val gender: UserGender
)