package com.example.markasiot

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.markasiot.presentation.sign_in.GoogleAuthUiClient
import com.example.markasiot.presentation.sign_in.SignInViewModel
import com.example.markasiot.presentation.home.HomeScreen
import com.example.markasiot.presentation.nav.RootNavGraph
import com.example.markasiot.presentation.nav.Screens
import com.example.markasiot.presentation.sign_in.AuthScreen
import com.example.markasiot.ui.theme.MarkasIoTTheme
import com.google.android.gms.auth.api.identity.Identity

import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val bottomNavHostController = rememberNavController()
            MarkasIoTTheme {
                IotApp(navController = navController)
            }
        }
    }
}



