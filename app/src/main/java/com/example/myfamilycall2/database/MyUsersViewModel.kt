package com.example.myfamilycall2.database

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyUsersViewModel(aplication:Application):AndroidViewModel(aplication) {

    val getAll:LiveData<List<MyUsers>>

    var addDialogState = mutableStateOf(false)
    var updateDialogState = mutableStateOf(false)

    var currentUser = mutableStateOf(MyUsers(0,0,"Current","User"))

    var visable = mutableStateOf(false)

    private val repository:MyUsersRepository
    init {
        val dao = MyUsersDatabase.getDatabase(aplication).dao()
        repository = MyUsersRepository(dao)
        getAll =repository.getAll
    }

    fun insertUser(user:MyUsers){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUser(user)
        }
    }

    fun updateUser(user:MyUsers){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUser(user)
        }
    }

    fun deleteUser(user:MyUsers){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }
}

