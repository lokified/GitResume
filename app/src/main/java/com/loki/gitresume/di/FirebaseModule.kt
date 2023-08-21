package com.loki.gitresume.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.loki.gitresume.data.remote.firebase.AuthRepositoryImpl
import com.loki.gitresume.data.remote.repository.ResumeRepositoryImpl
import com.loki.gitresume.domain.repository.AuthRepository
import com.loki.gitresume.domain.repository.ResumeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideAuthentication(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideStorage(): FirebaseStorage = Firebase.storage

    @Provides
    @Singleton
    fun provideFirebaseAuth(auth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(
        @ApplicationContext context: Context,
        firebaseStorage: FirebaseStorage
    ) : ResumeRepository {
        return ResumeRepositoryImpl(
            context = context,
            storage = firebaseStorage
        )
    }
}