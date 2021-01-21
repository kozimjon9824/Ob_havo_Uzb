package com.uzbsoft.ob_havo_uzb.di

import com.uzbsoft.ob_havo_uzb.repository.MainRepository
import com.uzbsoft.ob_havo_uzb.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit=Retrofit.Builder().
    baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =retrofit.create(
        ApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): MainRepository =
        MainRepository(apiService)

}