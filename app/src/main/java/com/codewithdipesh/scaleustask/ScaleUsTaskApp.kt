package com.codewithdipesh.scaleustask

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ScaleUsTaskApp : Application() {
   override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}