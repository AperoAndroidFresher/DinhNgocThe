package com.example.dinhngocthe.domain.repository

import com.example.dinhngocthe.data.local.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserByUsernameAndPassword(username: String, password: String) : User?
    fun getUserByUserId(userId: Long): Flow<User>
    suspend fun insertUser(user: User)
    suspend fun updateProfile(
        fullName: String,
        phoneNumber: String,
        universityName: String,
        description: String,
        avatarUri: String,
        userId: Long
    )
}