package com.example.transcore.di

import com.example.transcore.BuildConfig
import com.example.transcore.data.api.TranslateApiService
import com.example.transcore.domain.repos.TranslateRepository
import com.example.transcore.data.repository.TranslateRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.TRANSLATE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideTranslateApiService(retrofit: Retrofit): TranslateApiService =
        retrofit.create(TranslateApiService::class.java)

    @Provides
    @Singleton
    fun provideTranslateRepository(apiService: TranslateApiService): TranslateRepository =
        TranslateRepositoryImpl(apiService)

}
