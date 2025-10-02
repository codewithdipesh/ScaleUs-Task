package com.codewithdipesh.scaleustask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.codewithdipesh.scaleustask.presentation.authScreen.AuthViewModel
//import com.codewithdipesh.authScreen.presentation.scaleustask.AuthViewModel
import com.codewithdipesh.scaleustask.presentation.navigation.AppNavHost
import com.codewithdipesh.scaleustask.ui.theme.ScaleUsTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel by viewModels<AuthViewModel>()
        setContent {
            ScaleUsTaskTheme {
                val navController = rememberNavController()

                AppNavHost(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
        }
    }
}
