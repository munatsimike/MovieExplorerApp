package com.example.movieexplorerapp.di

import com.example.movieexplorerapp.data.remote.api.MovieService
import com.example.movieexplorerapp.data.remote.repo.RemoteMovieRepoImp
import com.example.movieexplorerapp.data.remote.repo.RemoteMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieRepoModule {

    @Singleton
    @Provides
    fun provideRemoteMovieRepoImp(
        movieService: MovieService,
    ): RemoteMovieRepoImp = RemoteMovieRepoImp(movieService)

    @Singleton
    @Provides
    fun provideRemoteMovieRepo(
        repoImp: RemoteMovieRepoImp
    ): RemoteMovieRepository = repoImp

}