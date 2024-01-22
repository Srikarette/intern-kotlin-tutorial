package com.example.tutorial.mapper

import com.example.tutorial.dto.UserGetByIdRes
import com.example.tutorial.dto.UserListGetRes
import com.example.tutorial.dto.enums.UserGender
import com.example.tutorial.entity.User

class UserGetByIdResMapper private constructor(){
    companion object{
        fun toUserGetByIdRes(user: User): UserGetByIdRes {
            return UserGetByIdRes(
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