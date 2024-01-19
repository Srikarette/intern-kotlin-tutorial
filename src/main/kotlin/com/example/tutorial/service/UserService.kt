package com.example.tutorial.service

import com.example.tutorial.entity.User
import com.example.tutorial.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getUsers(): List<User> {
        return userRepository.findAll()
    }
}