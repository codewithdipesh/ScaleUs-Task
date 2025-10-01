package com.codewithdipesh.scaleuptask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.codewithdipesh.scaleuptask.presentation.authScreen.AuthViewModel
//import com.codewithdipesh.scaleuptask.presentation.authScreen.AuthViewModel
import com.codewithdipesh.scaleuptask.presentation.navigation.AppNavHost
import com.codewithdipesh.scaleuptask.ui.theme.ScaleUpTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel by viewModels<AuthViewModel>()
        setContent {
            ScaleUpTaskTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
        }
    }
}
