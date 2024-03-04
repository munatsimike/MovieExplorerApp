package com.example.movieexplorerapp.di

import android.content.Context
import com.example.movieexplorerapp.data.local.preferences.EncryptedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Singleton
    @Provides
    fun provideEncryptedSharedPreference(@ApplicationContext context: Context): EncryptedPreferenceManager {
        return EncryptedPreferenceManager(context)
    }
}