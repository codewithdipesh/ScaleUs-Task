package com.codewithdipesh.scaleustask.di

import com.codewithdipesh.scaleustask.data.repo.AuthRepositoryImpl
import com.codewithdipesh.scaleustask.domain.repo.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideRepo(
        auth: FirebaseAuth
    ) : AuthRepository {
        return AuthRepositoryImpl(auth)
    }

}