package com.example.tutorial.dto

import com.example.tutorial.dto.enums.UserGender
import java.util.UUID

data class UserCreateRes(
    val id: UUID?,
    val firstName: String,
    val lastName: String,
    val email: String? = null,
    val address: String? = null,
    val gender: UserGender
)
