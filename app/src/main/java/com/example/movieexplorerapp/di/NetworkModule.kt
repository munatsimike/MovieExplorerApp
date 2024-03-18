package com.example.movieexplorerapp.di

import android.content.Context
import com.example.movieexplorerapp.BuildConfig
import com.example.movieexplorerapp.data.local.preferences.EncryptedPreferenceManager
import com.example.movieexplorerapp.data.remote.api.Constants.BASE_URL
import com.example.movieexplorerapp.data.remote.api.MovieService
import com.example.movieexplorerapp.data.service.api.APIKeyInterceptor
import com.example.movieexplorerapp.data.service.api.APIKeyProvider
import com.example.movieexplorerapp.data.service.api.APIKeyProviderImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAPIkey(manager: EncryptedPreferenceManager): APIKeyProvider {
        return APIKeyProviderImpl(manager)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        apiKeyProvider: APIKeyProvider,
        @ApplicationContext context: Context
    ): OkHttpClient {
        // Define cache size, e.g., 10 MB
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB

        // Create a Cache object with a specified size
        val cache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient().newBuilder()
            .cache(cache) // Add the Cache object to the OkHttpClient
            .addInterceptor(APIKeyInterceptor(apiKeyProvider))
            .also { client ->
                if (BuildConfig.DEBUG) {
                    val logger = HttpLoggingInterceptor()
                    logger.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logger)
                }
            }
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(okHttpClient).build()

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }
}