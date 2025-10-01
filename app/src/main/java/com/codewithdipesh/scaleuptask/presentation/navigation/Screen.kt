package com.codewithdipesh.scaleuptask.presentation.navigation

sealed class Screen(val path : String){
    object Login : Screen("login")
    object Register : Screen("register")
    object OtpScreen : Screen("otp_screen")
}
