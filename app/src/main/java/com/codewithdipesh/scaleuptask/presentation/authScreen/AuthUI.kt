package com.codewithdipesh.scaleuptask.presentation.authScreen

data class AuthUI(
    val phoneNumber: String = "",
    val phoneCountryCode: String = "+91",
    val otp: String = "",
    val isOtpSent: Boolean = false,
    val verificationId: String = "",
    val isLoading: Boolean = false,
    val isVerified: Boolean = false,
    val resendTimer: Int = 0,
    val authScreen: AuthScreen = AuthScreen.LOGIN
)

enum class AuthScreen{
    LOGIN,
    OTP_VERIFY,
    SIGN_UP
}