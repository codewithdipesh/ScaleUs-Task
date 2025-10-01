package com.codewithdipesh.scaleuptask.domain.model

sealed class AuthResult {
    data class Success(val userId: String) : AuthResult()
    data class CodeSent(val verificationId: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
    object Loading : AuthResult()
}