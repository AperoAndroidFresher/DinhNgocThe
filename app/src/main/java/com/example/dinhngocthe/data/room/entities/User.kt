package com.example.dinhngocthe.data.room.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val avatarUri: Uri? = null,
    val fullName: String = "",
    val phoneNumber: String = "",
    val universityName: String = "",
    val description: String = ""
)