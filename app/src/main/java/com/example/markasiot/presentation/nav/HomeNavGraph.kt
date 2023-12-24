package com.example.markasiot.presentation.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.markasiot.data.sampleUserData
import com.example.markasiot.presentation.AppViewModelProvider
import com.example.markasiot.presentation.activity.GraphScreen
import com.example.markasiot.presentation.home.HomeScreenContent
import com.example.markasiot.presentation.home.HomeViewModel
import com.example.markasiot.presentation.profile.ProfileScreen
import com.example.markasiot.presentation.sign_in.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavGraph(
    ViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onSignOut: () -> Unit,
    navController: NavHostController){

    val userData by ViewModel.userData.collectAsStateWithLifecycle()
    val homeDataList by ViewModel.homeDataList.collectAsStateWithLifecycle()
    val homeSwitchList by ViewModel.relayDataList.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        route = Graph.Home,
        startDestination = Screens.Home.route
    ){

        composable(route = Screens.Home.route){
            HomeScreenContent(
                userData = userData,
                homeDataList = homeDataList.IoTDataList,
                homeSwitchList = homeSwitchList.switchDataList,
                onCheckedChange = { isChecked, index ->
                    ViewModel.updateSwitchState(index+1, isChecked)
                })
        }

        composable(route = Screens.Profile.route){
            ProfileScreen(
                userData = userData,
                onSignOutClick = {
                    coroutineScope.launch {
                    ViewModel.signOut()
                        onSignOut()
                    }
                })
        }

        composable(route = Screens.Activity.route){
            GraphScreen()
        }
    }
}