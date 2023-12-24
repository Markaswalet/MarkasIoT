package com.example.markasiot.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.markasiot.data.DataRepository
import com.example.markasiot.data.IotData
import com.example.markasiot.data.SwitchData
import com.example.markasiot.data.dataIoT
import com.example.markasiot.data.sampleSwitchData
import com.example.markasiot.presentation.sign_in.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class HomeViewModel (private val dataRepository: DataRepository): ViewModel(){

    private val _userData = MutableStateFlow<UserData?>(UserData())
    val userData: StateFlow<UserData?> = _userData

    val googleAuthUiClient = dataRepository.googleAuthClient



    val userIdFlow = userData.filterNotNull()?.map { it.userId }
    val relayDataList: StateFlow<HomeSwitchList> =
        userIdFlow?.flatMapLatest { userId ->
            dataRepository.getData("Users/$userId/relayData", SwitchData::class.java)
                .map { HomeSwitchList(it) }

        }?.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = HomeSwitchList()
        ) ?: flow { emit(HomeSwitchList()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = HomeSwitchList()
            )

    val homeDataList: StateFlow<HomeDataList> =
        userIdFlow?.flatMapLatest { userId ->
            dataRepository.getData("Users/$userId/iotData", IotData::class.java)
                .map { HomeDataList(it) }

        }?.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = HomeDataList()
        ) ?: flow { emit(HomeDataList()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = HomeDataList()
            )

    val viewState: StateFlow<ViewState> =
        dataRepository.hasLoggedIn
            .map {hasLoggedIn ->
                if (hasLoggedIn){
                    ViewState.LoggedIn
                } else {
                    ViewState.NotLoggedIn
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ViewState.Loading
            )
    init {
        fetchUserData()

    }
    private fun fetchUserData(){
        viewModelScope.launch {
            _userData.value = dataRepository.googleAuthClient.getSignedInUser()
        }
    }



    fun updateSwitchState(id: Int, newValue: Boolean){
        dataRepository.sendData("Users/${userData.value?.userId}/relayData/$id/active", newValue)
    }

    suspend fun signOut(){
        dataRepository.googleAuthClient.signOut()
    }
}

data class HomeDataList(val IoTDataList: List<IotData> = dataIoT)
data class HomeSwitchList(val switchDataList: List<SwitchData> = sampleSwitchData)

sealed class ViewState{
    data object LoggedIn: ViewState()
    data object Loading: ViewState()
    data object NotLoggedIn: ViewState()
}