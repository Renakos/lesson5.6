package com.example.lesson54.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lesson54.data.model.Post

@Database(entities = [Post::class], version = 4)

abstract class AppDataBase : RoomDatabase() {
        abstract fun postDao(): PostDao

        companion object {
            private var INSTANCE: AppDataBase? = null

            fun getDataBase(context: Context): AppDataBase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext, AppDataBase::class.java, "post_database"
                    ).allowMainThreadQueries().build()
                    INSTANCE = instance
                    instance
                }
            }
        }
}