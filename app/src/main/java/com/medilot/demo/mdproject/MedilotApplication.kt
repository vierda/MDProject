package com.medilot.demo.mdproject

import android.app.Application
import android.arch.persistence.room.Room
import com.medilot.demo.mdproject.common.data.database.AppDatabase

class MedilotApplication : Application() {

    companion object {

        lateinit var _instance: MedilotApplication

        fun getInstance(): MedilotApplication {
            return _instance
        }
    }

    private val DATABASE_NAME = "MedilotDb"
    private var database: AppDatabase? = null


    override fun onCreate() {
        super.onCreate()
        _instance = this

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    fun getDatabase(): AppDatabase? {
        return database
    }
}