package com.example.tutorial.dto

import com.example.tutorial.dto.enums.UserGender
import java.util.UUID

data class UserUpdateByIdRes(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String?,
    val address: String?,
    val gender: UserGender
)