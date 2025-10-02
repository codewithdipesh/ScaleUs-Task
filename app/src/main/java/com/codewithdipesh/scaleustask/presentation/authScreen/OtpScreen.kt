package com.codewithdipesh.scaleustask.presentation.authScreen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codewithdipesh.scaleustask.domain.model.AuthResult
import com.codewithdipesh.scaleustask.presentation.authScreen.elements.AnimatedOtpField
import com.codewithdipesh.scaleustask.presentation.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OtpScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val state by viewModel.authState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isError by rememberSaveable { mutableStateOf(false) }

    BackHandler {
        navController.popBackStack()
        viewModel.clearOtp()
    }

    //timer
    LaunchedEffect(Unit) {
        viewModel.runTimer()
    }
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            Log.d("AuthViewModel",event.toString())
            when (event) {
                is AuthResult.Error -> {
                    scope.launch {
                        isError = true
                        delay(1000) //a red bounce effect
                        isError = false
                    }
                }
                is AuthResult.Success -> {
                    //home screen
                    scope.launch {
                        Toast.makeText(context, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.HomeScreen.path)
                    }
                }
                is AuthResult.CodeSent ->{
                    viewModel.runTimer()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ){innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp)
                    .padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){
                //title
                Text(
                    text = "Verify Phone",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Code has been sent to ${state.phoneCountryCode+state.phoneNumber}",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(Modifier.height(24.dp))
                AnimatedOtpField(
                    onOtpEntered = {
                        viewModel.enterOtp(it)
                    },
                    isError = isError
                )
                Spacer(Modifier.height(32.dp))
                Text(
                    text = "Didn't receive the code?",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Resend Code ",
                        style = TextStyle(
                            color = if(state.resendTimer == 0) MaterialTheme.colorScheme.primary
                                   else MaterialTheme.colorScheme.onBackground.copy(alpha =0.5f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.clickable {
                            if(state.resendTimer == 0) {
                                viewModel.resendVerificationCode(context as Activity)
                            }
                        }
                    )
                    if(state.resendTimer != 0) {
                        Text(
                            text = "${state.resendTimer/60}m ${state.resendTimer%60}s",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                }

            }

            //button
            Box(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = if(state.otp.length == 6) 1f else 0.5f))
                    .clickable{
                        if(state.otp.length == 6){
                            viewModel.verifyOtp()
                        }
                    },
                contentAlignment = Alignment.Center
            ){
                if(state.isLoading){
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                }else{
                    Text(
                        text = "Confirm",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
    }
}