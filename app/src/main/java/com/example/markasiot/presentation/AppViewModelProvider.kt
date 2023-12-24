package com.example.markasiot.presentation

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.markasiot.IoTApplication
import com.example.markasiot.presentation.home.HomeViewModel
import com.example.markasiot.presentation.sign_in.SignInViewModel

object AppViewModelProvider{
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(iotApplication().container.dataRepository)
        }

        initializer {
            SignInViewModel(iotApplication().container.dataRepository)
        }
    }
}

fun CreationExtras.iotApplication(): IoTApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as IoTApplication)
