package com.codewithdipesh.scaleustask.presentation.authScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codewithdipesh.scaleustask.R
import com.codewithdipesh.scaleustask.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val state by viewModel.authState.collectAsState()

    LaunchedEffect(Unit) {
        delay(2500)
        
        // Navigate based on auth
        if (state.isVerified) {
            navController.navigate(Screen.HomeScreen.path) {
                popUpTo(Screen.SplashScreen.path) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Login.path) {
                popUpTo(Screen.SplashScreen.path) { inclusive = true }
            }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        //app icon
        Image(
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .align(Alignment.Center)
        )
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
                .size(32.dp),
            color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
            strokeWidth = 3.dp
        )
    }
}