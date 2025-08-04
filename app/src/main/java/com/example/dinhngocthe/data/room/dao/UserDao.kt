package com.example.dinhngocthe.data.room.dao

import android.net.Uri
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dinhngocthe.data.room.entities.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("""
        UPDATE user 
        SET name = :name, phoneNumber = :phoneNumber, universityName = :university, describeYourSelf = :describe, avatar = :avatar
        WHERE id = :id
    """)
    suspend fun updateProfile(name: String, phoneNumber: String, university: String, describe: String, avatar: String, id: Long)

    @Query("""
        SELECT *
        FROM user
        WHERE username = :username and password = :password
    """)
    suspend fun getUserByUsernameAndPassword(username: String, password: String) : User?

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: Long) : User
}