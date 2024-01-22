package com.example.tutorial.mapper

import com.example.tutorial.dto.UserDeleteByIdRes
import com.example.tutorial.entity.User

class UserDeleteByIdResMapper private constructor(){
    companion object{
        fun toUserDeleteByIdRes(user: User): UserDeleteByIdRes {
            return UserDeleteByIdRes(
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