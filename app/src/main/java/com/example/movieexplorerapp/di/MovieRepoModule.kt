package com.example.movieexplorerapp.di

import com.example.movieexplorerapp.data.local.LocalMovieRepoImp
import com.example.movieexplorerapp.data.remote.api.MovieService
import com.example.movieexplorerapp.data.remote.repo.RemoteMovieRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MovieRepoModule {
    @Provides
    fun provideRemoteMovieRepo(
        movieService: MovieService,
        localMovieRepoImp: LocalMovieRepoImp
    ): RemoteMovieRepoImp = RemoteMovieRepoImp(movieService,  localMovieRepoImp)
}