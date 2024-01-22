package com.example.tutorial.repository

import com.example.tutorial.entity.User
import com.example.tutorial.entity.viewmodel.UserFullNameGetListProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    @Query(
        """
            SELECT
                id,
                CONCAT(first_name, ' ', last_name) AS fullName
            FROM
                users;
        """,
        nativeQuery = true,
    )
    fun getAllUserFullName(): List<UserFullNameGetListProjection>
}