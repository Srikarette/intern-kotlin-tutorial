package com.example.tutorial.mapper

import com.example.tutorial.dto.UserUpdateByIdRes
import com.example.tutorial.entity.User

class UserUpdateByIdResMapper private constructor(){
    companion object{
        fun toUserUpdateByIdRes(user: User): UserUpdateByIdRes {
            return UserUpdateByIdRes(
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