package com.example.markasiot

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.markasiot.presentation.nav.BottomNav
import com.example.markasiot.presentation.nav.Graph
import com.example.markasiot.presentation.nav.HomeNavGraph
import com.example.markasiot.presentation.nav.authNavGraph
import com.example.markasiot.presentation.nav.homeNavGraph
import com.example.markasiot.ui.theme.MarkasIoTTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IotApp(navController: NavHostController){
    MarkasIoTTheme {
        Surface (
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 5.dp
        ){
            Scaffold(
                bottomBar = {
                    BottomNav(navController = navController)
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Graph.Home)
                    {
                        homeNavGraph(navController)
                        authNavGraph(navController)
                    }
                }
            }
        }
    }
}