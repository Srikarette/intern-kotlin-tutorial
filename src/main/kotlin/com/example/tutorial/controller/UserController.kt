package com.example.tutorial.controller

import com.example.tutorial.dto.UserCreateReq
import com.example.tutorial.dto.UserCreateRes
import com.example.tutorial.dto.UserGetByIdReq
import com.example.tutorial.dto.UserGetByIdRes
import com.example.tutorial.dto.UserListGetRes
import com.example.tutorial.mapper.UserCreateResMapper
import com.example.tutorial.mapper.UserGetByIdResMapper
import com.example.tutorial.mapper.UserListGetResMapper
import com.example.tutorial.service.UserService
import org.springframework.web.bind.annotation.PostMapping
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
    fun getUserById(req: UserGetByIdReq): UserGetByIdRes {
        val user = userService.getUserById(req)
        return UserGetByIdResMapper.toUserGetByIdRes(user)
    }

    @PostMapping("/create")
    fun createUser(req: UserCreateReq): UserCreateRes {
        val user = userService.createUser(req)
        return UserCreateResMapper.toUserCreateRes(user)
    }

}