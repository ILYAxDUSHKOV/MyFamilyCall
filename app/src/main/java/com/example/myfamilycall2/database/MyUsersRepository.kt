package com.example.myfamilycall2.database

import androidx.lifecycle.LiveData

class MyUsersRepository(private val dao:dao) {

    val getAll:LiveData<List<MyUsers>> = dao.getAll()


    suspend fun insertUser(user: MyUsers){
        dao.insertUser(user)
    }

    suspend fun updateUser(user: MyUsers){
        dao.updateUser(user)
    }

    suspend fun deleteUser(user: MyUsers){
        dao.deleteUser(user)
    }

}