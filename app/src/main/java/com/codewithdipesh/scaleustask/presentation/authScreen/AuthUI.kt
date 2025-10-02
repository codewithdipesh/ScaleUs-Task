package com.codewithdipesh.scaleustask.presentation.authScreen

data class AuthUI(
    val phoneNumber: String = "",
    val phoneCountryCode: String = "+91",
    val otp: String = "",
    val isOtpSent: Boolean = false,
    val verificationId: String = "",
    val isLoading: Boolean = false,
    val isVerified: Boolean = false,
    val resendTimer: Int = 0
)