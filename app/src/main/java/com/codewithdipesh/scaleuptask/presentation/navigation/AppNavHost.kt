package com.codewithdipesh.scaleuptask.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codewithdipesh.scaleuptask.presentation.authScreen.AuthViewModel
import com.codewithdipesh.scaleuptask.presentation.authScreen.OtpScreen
import com.codewithdipesh.scaleuptask.presentation.authScreen.SignInScreen
import com.codewithdipesh.scaleuptask.presentation.authScreen.SignUpScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel
){

    NavHost(
        navController = navController,
        startDestination = Screen.Register.path,
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
    ){
        composable(Screen.Register.path){
            SignUpScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }
        composable(Screen.Login.path){
            SignInScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }
        composable(Screen.OtpScreen.path){
            OtpScreen(
                viewModel = authViewModel,
                navController = navController
            )
        }
    }

}