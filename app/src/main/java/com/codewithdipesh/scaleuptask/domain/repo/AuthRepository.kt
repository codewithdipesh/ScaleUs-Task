package com.codewithdipesh.scaleuptask.domain.repo

import android.app.Activity
import com.codewithdipesh.scaleuptask.domain.model.AuthResult
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun sendOtp(
        phoneNumber: String,
        activity: Activity,
        resendToken: PhoneAuthProvider.ForceResendingToken? = null // if resending then it will apply
    ): Flow<AuthResult>
    suspend fun verifyOtp(verificationId: String, otp: String): AuthResult
    fun isUserLoggedIn(): Boolean
}