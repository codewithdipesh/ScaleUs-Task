package com.codewithdipesh.scaleuptask.presentation.authScreen

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithdipesh.scaleuptask.domain.model.AuthResult
import com.codewithdipesh.scaleuptask.domain.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _authState = MutableStateFlow(AuthUI())
    val authState = _authState.asStateFlow()

    private val _uiEvent = Channel<AuthResult>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        checkAuthState()
    }

    fun checkAuthState() {
        _authState.update {
            it.copy(
                isVerified = repository.isUserLoggedIn()
            )
        }
    }

    fun sendVerificationCode(activity: Activity) {
        viewModelScope.launch {
            repository.sendOtp(_authState.value.phoneCountryCode+_authState.value.phoneNumber, activity)
                .collect { result ->
                    handleAuthResult(result)
                }
        }
    }

    fun resendVerificationCode(activity: Activity) {
        if(_authState.value.isOtpSent && _authState.value.resendTimer == 0) {
            viewModelScope.launch {
                repository.sendOtp(_authState.value.phoneCountryCode+_authState.value.phoneNumber, activity)
                    .collect { result ->
                        handleAuthResult(result)
                    }
            }
        }
    }

    fun verifyOtp(){
        val verificationId = _authState.value.verificationId
        val otp = _authState.value.otp
        if (verificationId.isEmpty() || otp.isEmpty()){
            if(verificationId.isEmpty()){
                //show toast or snack bar
                _uiEvent.trySend(
                    AuthResult.Error(
                        "Verification Id is Empty"
                    )
                )
            }
            if(otp.isEmpty()){
                //show toast or snack bar
                _uiEvent.trySend(
                    AuthResult.Error(
                        "Enter Correct OTP"
                    )
                )
            }
            return
        }
        viewModelScope.launch {
            val result = repository.verifyOtp(verificationId, otp)
            when (result) {
                is AuthResult.Success -> {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        isVerified = true
                    )
                }
                is AuthResult.Error -> {
                    _authState.value = _authState.value.copy(
                        isLoading = false
                    )
                }
                else -> {}
            }
            _uiEvent.trySend(result)
        }
    }

    fun enterOtp(otp: String) {
        _authState.value = _authState.value.copy(
            otp = otp
        )
    }

    fun enterPhoneNumber(phoneNumber: String,countryCode:String) {
        _authState.value = _authState.value.copy(
            phoneNumber = phoneNumber
        )
    }

    fun runTimer(){
       viewModelScope.launch {
           while(_authState.value.resendTimer > 0){
               _authState.value = _authState.value.copy(
                   resendTimer = _authState.value.resendTimer - 1
               )
               delay(1000)
           }
       }
    }

    private fun handleAuthResult(result: AuthResult) {
        Log.d("AuthViewModel", "handleAuthResult: $result")
        when (result) {
            is AuthResult.Loading -> {
                _authState.value = _authState.value.copy(
                    isLoading = true
                )
            }
            is AuthResult.CodeSent -> {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    isOtpSent = true,
                    verificationId = result.verificationId,
                    resendTimer = 120
                )
            }
            is AuthResult.Success -> {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    isVerified = true,
                    resendTimer = 0
                )
            }
            is AuthResult.Error -> {
                _authState.value = _authState.value.copy(
                    isLoading = false
                )
            }
        }
        _uiEvent.trySend(result)
    }

    fun clearOtp(){
        _authState.value = _authState.value.copy(
            otp = ""
        )
    }
    fun clearNumber(){
        _authState.value = _authState.value.copy(
            phoneNumber = "",
            phoneCountryCode = "+91"
        )
    }


}