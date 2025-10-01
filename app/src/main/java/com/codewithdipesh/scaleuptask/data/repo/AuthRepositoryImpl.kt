package com.codewithdipesh.scaleuptask.data.repo

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import com.codewithdipesh.scaleuptask.domain.model.AuthResult
import com.codewithdipesh.scaleuptask.domain.repo.AuthRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class AuthRepositoryImpl(
    private val auth: FirebaseAuth
): AuthRepository {
    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun sendOtp(
        phoneNumber: String,
        activity: Activity
    ): Flow<AuthResult> = callbackFlow {
        //loading
        trySend(AuthResult.Loading)

        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            //verification done
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                auth.signInWithCredential(credential)
                    .addOnSuccessListener { result ->
                        trySend(AuthResult.Success(result.user?.uid ?: ""))
                    }
                    .addOnFailureListener { exception ->
                        trySend(AuthResult.Error(exception.message ?: "Sign in failed"))
                    }
            }
            override fun onVerificationFailed(p0: FirebaseException) {
                trySend(AuthResult.Error(p0.message ?: "Verification failed"))
            }
            override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId, p1)
                trySend(AuthResult.CodeSent(verificationId))
            }

        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callback) // OnVerificationStateChangedCallbacks
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun verifyOtp(
        verificationId: String,
        otp: String
    ): AuthResult {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId, otp)
            val result = auth.signInWithCredential(credential).await()
            AuthResult.Success(result.user?.uid ?: "")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Verification failed")
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}