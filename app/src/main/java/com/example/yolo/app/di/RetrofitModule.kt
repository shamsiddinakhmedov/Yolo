package com.example.yolo.app.di

import com.example.yolo.BuildConfig
import com.example.yolo.app.common.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object RetrofitModule {
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(
//        httpClient: OkHttpClient
//    ): Retrofit {
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(httpClient)
//            .baseUrl(BASE_URL)
//            .validateEagerly(BuildConfig.DEBUG)
//            .build()
//    }
//}