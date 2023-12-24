package com.example.markasiot

import android.app.Application
import com.example.markasiot.data.AppContainer
import com.example.markasiot.data.AppDataContainer

class IoTApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(applicationContext)
    }
}