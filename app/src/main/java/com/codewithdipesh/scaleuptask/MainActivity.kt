package com.codewithdipesh.scaleuptask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codewithdipesh.scaleuptask.presentation.authScreen.AuthViewModel
//import com.codewithdipesh.scaleuptask.presentation.authScreen.AuthViewModel
import com.codewithdipesh.scaleuptask.presentation.authScreen.SignUpScreen
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
                SignUpScreen(authViewModel)
            }
        }
    }
}
