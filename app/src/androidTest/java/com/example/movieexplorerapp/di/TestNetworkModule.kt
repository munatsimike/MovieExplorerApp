package com.example.movieexplorerapp.di

import com.example.movieexplorerapp.data.remote.api.MovieService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestNetworkModule {

    @Singleton
    @Provides
    fun providesMockWebServer(): MockWebServer = MockWebServer()

    @Provides
    @Named("testMovieService")
    fun providesTestMovieService(moshi: Moshi, mockWebServer: MockWebServer): MovieService {
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create(MovieService::class.java)
    }
}