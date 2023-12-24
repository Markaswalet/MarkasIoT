package com.example.markasiot.presentation.nav

import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.markasiot.data.sampleUserData
import com.example.markasiot.presentation.AppViewModelProvider
import com.example.markasiot.presentation.home.HomeScreen
import com.example.markasiot.presentation.home.HomeViewModel
import com.example.markasiot.presentation.sign_in.AuthScreen
import com.example.markasiot.presentation.sign_in.LoginScreen
import com.example.markasiot.presentation.sign_in.SignInViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    viewModel: SignInViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController,
    bottomNavHostController: NavHostController){
    val context = LocalContext.current

    NavHost(
        navController = navController,
        route = Graph.Root,
        startDestination = Graph.Authtentication
    ){

        navigation(
            route = Graph.Authtentication,
            startDestination = "login"
        ){
            composable(route = "login"){
                val coroutineScope = rememberCoroutineScope()
                val googleAuthClient = viewModel.googleAuthUiClient
                val state by viewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(Unit){
                    if(googleAuthClient.getSignedInUser() != null){
                        navController.navigate(Graph.Home)
                        {
                            popUpTo(route = Graph.Authtentication){
                                inclusive = true
                            }
                        }
                    }
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if (result.resultCode == ComponentActivity.RESULT_OK){
                            coroutineScope.launch {
                                val signInResult = googleAuthClient.signWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)
                                Log.d("result", "${result.data}")
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = state.isSignInSuccessful){
                    if(state.isSignInSuccessful){
                        Toast.makeText(
                            context,
                            "Sign In Successfully",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate(Graph.Home)
                        {
                            popUpTo(route = Graph.Authtentication){
                                inclusive = true
                            }
                        }
                        viewModel.resetState()
                    }
                }

                AuthScreen(
                    state = state,
                    onSignInClick = {
                        coroutineScope.launch {
                            val signInIntentSender = googleAuthClient.SignIn()

                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    }

                )

            }
        }

        navigation(
            route = Graph.Home,
            startDestination = Screens.Home.route
        ){
            composable(route = Screens.Home.route){
                HomeScreen(
                    navController = bottomNavHostController,
                    onSignOut = {
                        navController.navigate(Graph.Root)
                        {
                            popUpTo(Graph.Home){
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

    }
}

object Graph{
    const val Root = "root_graph"
    const val Authtentication = "auth_graph"
    const val Home = "home_graph"
}