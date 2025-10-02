package com.codewithdipesh.scaleustask.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codewithdipesh.scaleustask.presentation.main.homescreen.HomeScreen
import com.codewithdipesh.scaleustask.presentation.main.notificationscreen.NotificationScreen
import com.codewithdipesh.scaleustask.presentation.main.settingsscreen.SettingsScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.path,
        modifier = modifier,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        popEnterTransition = {
            EnterTransition.None
        },
        popExitTransition = {
            ExitTransition.None
        },
    ) {
        composable(Screen.HomeScreen.path) {
            HomeScreen()
        }
        composable(Screen.NotificationScreen.path) {
            NotificationScreen()
        }
        composable(Screen.SettingsScreen.path) {
            SettingsScreen()
        }
    }
}
