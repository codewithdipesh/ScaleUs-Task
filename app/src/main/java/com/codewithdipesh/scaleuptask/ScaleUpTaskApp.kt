package com.codewithdipesh.scaleuptask

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ScaleUpTaskApp : Application() {
   override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}