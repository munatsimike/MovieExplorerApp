package com.example.movieexplorerapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieexplorerapp.data.local.database.MovieLocalDatabase
import com.example.movieexplorerapp.data.local.database.TEST_DB_NAME
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
    @Named(TEST_DB_NAME)
    fun provideTestDatabase(@ApplicationContext context: Context): MovieLocalDatabase =
        Room.inMemoryDatabaseBuilder(context = context, MovieLocalDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}