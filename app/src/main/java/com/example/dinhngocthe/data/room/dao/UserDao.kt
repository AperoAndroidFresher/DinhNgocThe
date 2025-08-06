package com.example.dinhngocthe.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dinhngocthe.data.room.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    // Used in the ProfileScreen
    @Query(
        """
        UPDATE user 
        SET fullName = :fullName, phoneNumber = :phoneNumber, universityName = :universityName, description = :description, avatarUri = :avatarUri
        WHERE userId = :userId
    """
    )
    suspend fun updateProfile(fullName: String, phoneNumber: String, universityName: String, description: String, avatarUri: String, userId: Long)

    @Query("""
        SELECT *
        FROM user
        WHERE username = :username and password = :password
    """)
    suspend fun getUserByUsernameAndPassword(username: String, password: String) : User?

    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUserByUserId(userId: Long) : Flow<User>
}