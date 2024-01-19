package com.example.tutorial.mapper

import com.example.tutorial.dto.UserListGetRes
import com.example.tutorial.entity.User

class UserListGetResMapper private constructor() {
    companion object {
        fun toUserListGetRes(users: List<User>): List<UserListGetRes> {
            return users.map { toUserList(it) }
        }

        private fun toUserList(user: User): UserListGetRes {
            return UserListGetRes(
                id = user.id!!,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                address = user.address,
                gender = user.gender
            )
        }
    }
}