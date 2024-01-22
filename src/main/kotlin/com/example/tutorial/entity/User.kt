package com.example.tutorial.entity

import com.example.tutorial.dto.enums.UserGender
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.AUTO
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable
import java.util.UUID

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = AUTO)
    val id: UUID? = null,
    @Column(name = "first_name")
    val firstName: String,
    @Column(name = "last_name")
    val lastName: String,
    @Column(name = "email")
    val email: String?,
    @Column(name = "address")
    val address: String?,
    @Column(name = "gender")
    @Enumerated(STRING)
    val gender: UserGender
): Serializable