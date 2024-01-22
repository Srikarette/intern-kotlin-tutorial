package com.example.tutorial.dto

import java.util.UUID

data class UserListGetAllFullNameRes(
    val users: List<UserData>
) {
    data class UserData(
        val id: UUID,
        val fullName: String
    )
}