package com.example.markasiot.presentation.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.markasiot.presentation.AppViewModelProvider
import com.example.markasiot.presentation.activity.GraphScreen
import com.example.markasiot.presentation.home.HomeScreen2
import com.example.markasiot.presentation.home.HomeScreenContent
import com.example.markasiot.presentation.home.HomeViewModel
import com.example.markasiot.presentation.profile.ProfileScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
    ){
    navigation(
        route = Graph.Home,
        startDestination = Screens.Home.route
    ){
        composable(Screens.Home.route){
            val viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

            HomeScreen2(
                viewModel = viewModel,
                onNavigateToLoginScreen = {
                    navController.navigate(Graph.Authtentication)
                    {
                        popUpTo(route = Graph.Home){
                            inclusive = true
                        }
                    }
                })
        }

        composable(Screens.Profile.route){
            val viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

            val userData by viewModel.userData.collectAsStateWithLifecycle()
            val coroutineScope = rememberCoroutineScope()

            ProfileScreen(
                userData = userData,
                onSignOutClick = {
                    coroutineScope.launch {
                        viewModel.signOut()
                        navController.navigate(Graph.Authtentication)
                        {
                            popUpTo(route = Graph.Home){
                                inclusive = true
                            }
                        }
                    }
                })
        }

        composable(route = Screens.Activity.route){
            GraphScreen()
        }
    }
}