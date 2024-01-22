package com.example.tutorial.mapper

import com.example.tutorial.dto.UserDeleteRes
import com.example.tutorial.dto.UserUpdateRes
import com.example.tutorial.entity.User

class UserDeleteByIdResMapper private constructor(){
    companion object{
        fun toUserDeleteByIdRes(user: User): UserDeleteRes {
            return UserDeleteRes(
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