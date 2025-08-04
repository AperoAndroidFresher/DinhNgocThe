package com.example.dinhngocthe.data.repository

import android.app.Application
import com.example.dinhngocthe.data.room.LocalDatabase
import com.example.dinhngocthe.data.room.entities.User

class UserRepository(context: Application) {
    private val localDatabase = LocalDatabase.Companion.getInstance(context)
    private val userDao = localDatabase.userDao()
    suspend fun getUserByUsernameAndPassword(username: String, password: String) : User? {
        return userDao.getUserByUsernameAndPassword(username, password)
    }

    suspend fun getUserById(id: Long): User {
        return userDao.getUserById(id)
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun updateProfile(
        name: String,
        phoneNumber: String,
        university: String,
        describe: String,
        avatar: String,
        id: Long
    ) {
        userDao.updateProfile(name, phoneNumber, university, describe, avatar, id)
    }
}