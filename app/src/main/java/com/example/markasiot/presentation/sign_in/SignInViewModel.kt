package com.example.markasiot.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.markasiot.data.DataRepository
import com.example.markasiot.data.sampleSwitchData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(private val dataRepository: DataRepository): ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _userData = MutableStateFlow<UserData?>(UserData())
    val userData: StateFlow<UserData?> = _userData

    val googleAuthUiClient = dataRepository.googleAuthClient

    init {
        fetchUserData()
    }

    fun onSignInResult(result: SignInResult){
        _state.update {it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        )}
    }

    fun setupSwitch(path: String){
        dataRepository.sendData(path, sampleSwitchData)
    }

    private fun fetchUserData(){
        viewModelScope.launch {
            _userData.value = dataRepository.googleAuthClient.getSignedInUser()
        }
    }

    fun resetState(){
        _state.update { SignInState() }
    }
}