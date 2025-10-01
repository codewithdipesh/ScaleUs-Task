package com.codewithdipesh.scaleuptask.di

import com.codewithdipesh.scaleuptask.data.repo.AuthRepositoryImpl
import com.codewithdipesh.scaleuptask.domain.repo.AuthRepository
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
    fun provideRepo() : AuthRepository {
        return AuthRepositoryImpl()
    }

}