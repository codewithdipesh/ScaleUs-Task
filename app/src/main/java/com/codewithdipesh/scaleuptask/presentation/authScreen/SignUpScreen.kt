package com.codewithdipesh.scaleuptask.presentation.authScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codewithdipesh.scaleuptask.R
import com.joelkanyi.jcomposecountrycodepicker.component.KomposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
//    navController: NavController
) {
    val state by viewModel.authState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val countryCodePickerState = rememberKomposeCountryCodePickerState(
        showCountryCode = true,
        showCountryFlag = true,
        defaultCountryCode = "in"
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ){innerPadding ->
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
                text = "Create your account",
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
                    viewModel.enterPhoneNumber(it)
                },
                modifier = Modifier
                    .fillMaxWidth()

            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Existing User?",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.End
                ),
                modifier = Modifier.fillMaxWidth()
            )


        }
    }
}