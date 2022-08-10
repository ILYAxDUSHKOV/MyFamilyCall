package com.example.myfamilycall2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface dao {
    @Query("SELECT * from users_table")
    fun getAll():LiveData<List<MyUsers>>

    @Insert
    suspend fun insertUser(user:MyUsers)

    @Update
    suspend fun updateUser(user: MyUsers)

    @Delete
    suspend fun deleteUser(user: MyUsers)
}