package com.example.dinhngocthe.data.repository

import android.app.Application
import com.example.dinhngocthe.data.local.LocalDatabase
import com.example.dinhngocthe.data.local.dao.UserDao
import com.example.dinhngocthe.data.local.entities.User
import kotlinx.coroutines.flow.Flow

class UserRepository(context: Application) {
    private val localDatabase: LocalDatabase = LocalDatabase.Companion.getInstance(context)
    private val userDao: UserDao = localDatabase.userDao()
    suspend fun getUserByUsernameAndPassword(username: String, password: String) : User? {
        return userDao.getUserByUsernameAndPassword(username, password)
    }

    fun getUserByUserId(userId: Long): Flow<User> {
        return userDao.getUserByUserId(userId)
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun updateProfile(
        fullName: String,
        phoneNumber: String,
        universityName: String,
        description: String,
        avatarUri: String,
        userId: Long
    ) {
        userDao.updateProfile(fullName, phoneNumber, universityName, description, avatarUri, userId)
    }
}