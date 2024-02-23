package com.example.movieexplorerapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieexplorerapp.data.local.database.DB_NAME
import com.example.movieexplorerapp.data.local.dao.MovieDao
import com.example.movieexplorerapp.data.local.database.LocalMovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDatabaseModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context): LocalMovieDatabase =
        Room.databaseBuilder(context = context, LocalMovieDatabase::class.java, DB_NAME).build()

    @Singleton
    @Provides
    fun provideMovieDao(database: LocalMovieDatabase): MovieDao = database.movieDao
}