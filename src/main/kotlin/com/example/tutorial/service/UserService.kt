package com.example.tutorial.service

import com.example.tutorial.config.dto.exception.CommonException
import com.example.tutorial.constant.ResponseConstants.GET_USER_SUCCESS
import com.example.tutorial.dto.UserCreateReq
import com.example.tutorial.dto.UserGetByIdReq
import com.example.tutorial.dto.enums.UserGender
import com.example.tutorial.entity.User
import com.example.tutorial.repository.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
) {
    @Cacheable(value = ["userList"], unless = "#result==null")
    fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    @Cacheable(value = ["user"], key = "#req.userId")
    fun getUserById(req: UserGetByIdReq): User {
        val existsUser = userRepository.findById(req.userId!!)
        if (existsUser.isEmpty) {
            throw CommonException(GET_USER_SUCCESS.getCode(), GET_USER_SUCCESS.getMessage())
        }
        return existsUser.get()
    }

    fun createUser(user: UserCreateReq): User {
        val userCreated = User(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            address = user.address,
            gender = user.gender
        )
        return userRepository.save(userCreated)
    }

}