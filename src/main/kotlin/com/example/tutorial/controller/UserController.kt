package com.example.tutorial.controller

import com.example.tutorial.dto.UserCreateReq
import com.example.tutorial.dto.UserCreateRes
import com.example.tutorial.dto.UserDeleteReq
import com.example.tutorial.dto.UserDeleteRes
import com.example.tutorial.dto.UserGetByIdReq
import com.example.tutorial.dto.UserGetByIdRes
import com.example.tutorial.dto.UserListGetRes
import com.example.tutorial.dto.UserUpdateReq
import com.example.tutorial.dto.UserUpdateRes
import com.example.tutorial.mapper.UserCreateResMapper
import com.example.tutorial.mapper.UserDeleteByIdResMapper
import com.example.tutorial.mapper.UserGetByIdResMapper
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

    @PostMapping("/get")
    fun getUserById(@Valid @RequestBody req: UserGetByIdReq): UserGetByIdRes {
        val user = userService.getUserById(req)
        return UserGetByIdResMapper.toUserGetByIdRes(user)
    }

    @PostMapping("/create")
    fun updateUserById(@Valid @RequestBody req: UserCreateReq): UserCreateRes {
        val user = userService.createUser(req)
        return UserCreateResMapper.toUserCreateRes(user)
    }

    @PostMapping("/update")
    fun putUser(@Valid @RequestBody req: UserUpdateReq): UserUpdateRes {
        val userToUpdate = userService.updateUserById(req)
        return UserUpdateByIdResMapper.toUserUpdateByIdRes(userToUpdate)
    }

    @PostMapping("/delete")
    fun deleteUser(@Valid @RequestBody req: UserDeleteReq): UserDeleteRes {
        val userToDelete = userService.deleteUser(req)
        return UserDeleteByIdResMapper.toUserDeleteByIdRes(userToDelete)
    }

}