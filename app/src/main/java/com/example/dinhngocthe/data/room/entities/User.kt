package com.example.dinhngocthe.data.room.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val avatar: Uri? = null,
    val name: String = "",
    val phoneNumber: String = "",
    val universityName: String = "",
    val describeYourSelf: String = ""
)