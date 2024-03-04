package com.example.movieexplorerapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieexplorerapp.data.local.database.LocalMovieDatabase
import com.example.movieexplorerapp.data.TEST_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestDatabaseModule {
    @Singleton
    @Provides
    @Named(TEST_DB)
    fun provideTestDatabase(@ApplicationContext context: Context): LocalMovieDatabase =
        Room.inMemoryDatabaseBuilder(context = context, LocalMovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}