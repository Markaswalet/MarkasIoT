package com.example.markasiot.presentation.nav

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomNav(navController: NavController){
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }

    val screens = listOf(
        Screens.Home,
        Screens.Profile,
        Screens.Activity
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavDestination = listNavItem.any { it.route == currentDestination?.route }

    if(bottomNavDestination){
        NavigationBar{
            listNavItem.forEachIndexed { index, navItem ->
                NavigationBarItem(
                    selected = index == navigationSelectedItem,
                    label = {
                        Text(
                            text = navItem.label,
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    onClick = {
                        navigationSelectedItem = index
                        navController.navigate(navItem.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Image(
                            painter = painterResource(id = navItem.icon),
                            contentDescription = navItem.label,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                )
            }

        }
    }

}