package com.example.tutorial.controller

import com.example.tutorial.dto.UserCreateReq
import com.example.tutorial.dto.UserCreateRes
import com.example.tutorial.dto.UserDeleteByIdReq
import com.example.tutorial.dto.UserDeleteByIdRes
import com.example.tutorial.dto.UserGetByIdReq
import com.example.tutorial.dto.UserGetByIdRes
import com.example.tutorial.dto.UserGetOrdersReq
import com.example.tutorial.dto.UserGetOrdersRes
import com.example.tutorial.dto.UserListGetAllFullNameRes
import com.example.tutorial.dto.UserListGetRes
import com.example.tutorial.dto.UserUpdateByIdReq
import com.example.tutorial.dto.UserUpdateByIdRes
import com.example.tutorial.mapper.UserCreateResMapper
import com.example.tutorial.mapper.UserDeleteByIdResMapper
import com.example.tutorial.mapper.UserGetByIdResMapper
import com.example.tutorial.mapper.UserGetOrdersResMapper
import com.example.tutorial.mapper.UserListGetFullNameResMapper
import com.example.tutorial.mapper.UserListGetResMapper
import com.example.tutorial.mapper.UserUpdateByIdResMapper
import com.example.tutorial.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping("/")
    fun getUserList(): List<UserListGetRes> {
        val users = userService.getUsers()
        return UserListGetResMapper.toUserListGetRes(users)
    }

    @PostMapping("/get/full/name")
    fun getUserFullNameList(): UserListGetAllFullNameRes {
        val users = userService.getUsersFullName()
        return UserListGetFullNameResMapper.toUserListGetFullNameRes(users)
    }

    @PostMapping("/get")
    fun getUserById(@Valid @RequestBody req: UserGetByIdReq): UserGetByIdRes {
        val user = userService.getUserById(req)
        return UserGetByIdResMapper.toUserGetByIdRes(user)
    }

    @PostMapping("/get/order")
    fun getUserOrderById(@Valid @RequestBody req: UserGetOrdersReq): UserGetOrdersRes {
        val user = userService.getUserOrderById(req)
        return UserGetOrdersResMapper.toUserGetOrdersRes(user)
    }


    @PostMapping("/create")
    fun updateUserById(@Valid @RequestBody req: UserCreateReq): UserCreateRes {
        val user = userService.createUser(req)
        return UserCreateResMapper.toUserCreateRes(user)
    }

    @PostMapping("/update")
    fun putUser(@Valid @RequestBody req: UserUpdateByIdReq): UserUpdateByIdRes {
        val userToUpdate = userService.updateUserById(req)
        return UserUpdateByIdResMapper.toUserUpdateByIdRes(userToUpdate)
    }

    @PostMapping("/delete")
    fun deleteUser(@Valid @RequestBody req: UserDeleteByIdReq): UserDeleteByIdRes {
        val userToDelete = userService.deleteUserById(req)
        return UserDeleteByIdResMapper.toUserDeleteByIdRes(userToDelete)
    }

}