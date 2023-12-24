package com.example.markasiot.presentation.nav

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.markasiot.presentation.AppViewModelProvider
import com.example.markasiot.presentation.sign_in.AuthScreen
import com.example.markasiot.presentation.sign_in.SignInViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.authNavGraph(navController: NavHostController){
    navigation(
        route = Graph.Authtentication,
        startDestination = "login"
    ){
        composable(route = "login"){
            val viewModel: SignInViewModel = viewModel(factory = AppViewModelProvider.Factory)

            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()
            val googleAuthClient = viewModel.googleAuthUiClient
            val state by viewModel.state.collectAsStateWithLifecycle()


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
                val userData = googleAuthClient.getSignedInUser()
                if(state.isSignInSuccessful){
                    viewModel.setupSwitch("Users/${userData?.userId}/relayData")
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
}