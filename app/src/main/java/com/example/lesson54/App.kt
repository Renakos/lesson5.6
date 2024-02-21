package com.example.lesson54

import android.app.Application
import com.example.lesson54.data.local.AppDataBase

class App : Application() {
    companion object {
        lateinit var db: AppDataBase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        db = AppDataBase.getDataBase(this)
    }
}
