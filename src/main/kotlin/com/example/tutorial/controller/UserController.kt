package com.example.tutorial.controller

import com.example.tutorial.dto.UserListGetRes
import com.example.tutorial.mapper.UserListGetResMapper
import com.example.tutorial.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping("/")
    fun getUserList(): List<UserListGetRes> {
        val users = userService.getUsers()
        return UserListGetResMapper.toUserListGetRes(users)
    }

}