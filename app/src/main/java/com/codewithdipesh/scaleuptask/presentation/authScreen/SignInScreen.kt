package com.codewithdipesh.scaleuptask.presentation.authScreen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codewithdipesh.scaleuptask.domain.model.AuthResult
import com.codewithdipesh.scaleuptask.presentation.navigation.Screen
import com.joelkanyi.jcomposecountrycodepicker.component.KomposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val state by viewModel.authState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val countryCodePickerState = rememberKomposeCountryCodePickerState(
        showCountryCode = true,
        showCountryFlag = true,
        defaultCountryCode = "in"
    )

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AuthResult.CodeSent -> {
                    navController.navigate(Screen.OtpScreen.path)
                }
                is AuthResult.Error -> {
                    scope.launch {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
                AuthResult.Loading -> {}
                is AuthResult.Success -> {}
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
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ){
                //title
                Text(
                    text = "Scaling Ideas into Impactful Digital Solutions",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.height(8.dp))
                //small line
                Text(
                    text = "LogIn your account",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(Modifier.height(40.dp))
                //enter mobile
                Text(
                    text = "Enter your mobile number",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(Modifier.height(8.dp))
                KomposeCountryCodePicker(
                    state = countryCodePickerState,
                    text = state.phoneNumber,
                    onValueChange = {
                        viewModel.enterPhoneNumber(it, countryCodePickerState.getCountryPhoneCode())
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()

                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "New User?",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .clickable(
                            indication = null, // disables effects of ripllingg
                            interactionSource = remember { MutableInteractionSource() }
                        ){
                            navController.navigate(Screen.Register.path){
                                popUpTo(Screen.Register.path)
                                launchSingleTop = true
                            }
                            viewModel.clearNumber()
                        }
                )

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
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable{
                        if(!state.isLoading){
                            viewModel.sendVerificationCode( context as Activity)
                        }
                    },
                contentAlignment = Alignment.Center
            ){
                if(state.isLoading){
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                }else{
                    Text(
                        text = "Sign In",
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