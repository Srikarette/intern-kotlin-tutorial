package com.example.tutorial.service

import com.example.tutorial.config.dto.exception.CommonException
import com.example.tutorial.constant.ResponseConstants.GET_USER_NOT_FOUND
import com.example.tutorial.dto.UserCreateReq
import com.example.tutorial.dto.UserDeleteReq
import com.example.tutorial.dto.UserGetByIdReq
import com.example.tutorial.dto.UserUpdateReq
import com.example.tutorial.dto.enums.UserGender
import com.example.tutorial.entity.User
import com.example.tutorial.repository.UserRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
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
            throw CommonException(GET_USER_NOT_FOUND.getCode(), GET_USER_NOT_FOUND.getMessage())
        }
        return existsUser.get()
    }

    fun createUser(req: UserCreateReq): User {
        val userCreated = User(
            firstName = req.firstName!!,
            lastName = req.lastName!!,
            email = req.email,
            address = req.address,
            gender = req.gender!!
        )
        return userRepository.save(userCreated)
    }

    @CachePut(value = ["updatedUser"], key = "#req.userId")
    fun updateUserById(req: UserUpdateReq): User {
        val existingUser = userRepository.findById(req.userId!!)
        if (existingUser.isEmpty) {
            throw CommonException(GET_USER_NOT_FOUND.getCode(), GET_USER_NOT_FOUND.getMessage())
        }

        val original = existingUser.get()

        val updatedUser = original.copy(
            firstName = req.firstName ?: original.firstName,
            lastName = req.lastName ?: original.lastName,
            email = req.email ?: original.email,
            address = req.address ?: original.address,
            gender = req.gender ?:original.gender
        )

        return userRepository.save(updatedUser)
    }

    @CacheEvict(value = ["deletedUser"], key = "#req.userId", allEntries = true)
    fun deleteUser(req: UserDeleteReq): User {
        val existingUser = userRepository.findById(req.userId!!)
        if (existingUser.isEmpty) {
            throw CommonException(GET_USER_NOT_FOUND.getCode(), GET_USER_NOT_FOUND.getMessage())
        }
        userRepository.deleteById(req.userId)
        return existingUser.get()
    }

}