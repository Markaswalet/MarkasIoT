package com.example.markasiot.data

import android.content.Context

interface AppContainer {
    val dataRepository: DataRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val dataRepository: DataRepository by lazy {
        DataRepository(context)
    }

}