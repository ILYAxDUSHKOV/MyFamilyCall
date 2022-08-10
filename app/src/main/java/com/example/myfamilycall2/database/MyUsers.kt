package com.example.myfamilycall2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class MyUsers(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val image:Int,
    val name:String,
    val number:String
)