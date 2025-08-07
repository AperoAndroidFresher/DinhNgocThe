package com.example.dinhngocthe.data.repository

import android.app.Application
import com.example.dinhngocthe.data.local.LocalDatabase
import com.example.dinhngocthe.data.local.dao.UserDao
import com.example.dinhngocthe.data.local.entities.User
import com.example.dinhngocthe.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(context: Application) : UserRepository {
    private val localDatabase: LocalDatabase = LocalDatabase.Companion.getInstance(context)
    private val userDao: UserDao = localDatabase.userDao()
    override suspend fun getUserByUsernameAndPassword(username: String, password: String) : User? {
        return userDao.getUserByUsernameAndPassword(username, password)
    }

    override fun getUserByUserId(userId: Long): Flow<User> {
        return userDao.getUserByUserId(userId)
    }

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun updateProfile(
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