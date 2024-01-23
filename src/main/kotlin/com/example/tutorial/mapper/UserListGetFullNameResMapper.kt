package com.example.tutorial.mapper

import com.example.tutorial.dto.UserListGetAllFullNameRes
import com.example.tutorial.entity.viewmodel.UserFullNameGetListProjection


class UserListGetFullNameResMapper private constructor() {
    companion object {
        fun toUserListGetFullNameRes(users: List<UserFullNameGetListProjection>): UserListGetAllFullNameRes {
            return UserListGetAllFullNameRes(
                users = users.map { toUserList(it) }
            )
        }

        private fun toUserList(user: UserFullNameGetListProjection): UserListGetAllFullNameRes.UserData {
            return UserListGetAllFullNameRes.UserData(
                id = user.id,
                fullName = user.fullName
            )
        }
    }
}