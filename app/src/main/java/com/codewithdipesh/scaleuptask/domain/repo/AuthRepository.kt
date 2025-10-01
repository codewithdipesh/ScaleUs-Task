package com.codewithdipesh.scaleuptask.domain.repo

import android.app.Activity
import com.codewithdipesh.scaleuptask.domain.model.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun sendOtp(
        phoneNumber: String,
        activity: Activity
    ): Flow<AuthResult>
    suspend fun verifyOtp(verificationId: String, otp: String): AuthResult
    fun isUserLoggedIn(): Boolean
}