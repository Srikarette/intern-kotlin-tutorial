package com.example.tutorial.mapper

import com.example.tutorial.dto.UserCreateRes
import com.example.tutorial.dto.UserGetByIdRes
import com.example.tutorial.entity.User

class UserCreateResMapper private constructor() {
    companion object{
        fun toUserCreateRes(user: User): UserCreateRes {
            return UserCreateRes(
                id = user.id,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                address = user.address,
                gender = user.gender
            )
        }
    }
}