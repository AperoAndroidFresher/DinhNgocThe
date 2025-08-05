package com.example.dinhngocthe.data.repository

import android.app.Application
import com.example.dinhngocthe.data.room.LocalDatabase
import com.example.dinhngocthe.data.room.dao.UserDao
import com.example.dinhngocthe.data.room.entities.User

class UserRepository(context: Application) {
    private val localDatabase: LocalDatabase = LocalDatabase.Companion.getInstance(context)
    private val userDao: UserDao = localDatabase.userDao()
    suspend fun getUserByUsernameAndPassword(username: String, password: String) : User? {
        return userDao.getUserByUsernameAndPassword(username, password)
    }

    suspend fun getUserByUserId(userId: Long): User {
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