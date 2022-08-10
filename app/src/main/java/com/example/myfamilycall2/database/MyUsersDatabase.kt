package com.example.myfamilycall2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyUsers::class], version = 1, exportSchema = false)
abstract class MyUsersDatabase: RoomDatabase() {
    abstract fun dao():dao

    companion object {
        @Volatile
        private var INSTANCE:MyUsersDatabase? = null

        fun getDatabase(context: Context):MyUsersDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,MyUsersDatabase::class.java,"user_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}