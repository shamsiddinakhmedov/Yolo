package com.example.yolo.app.di

import android.app.Application
import com.example.yolo.app.common.Constants
import com.example.yolo.data.api.PhotoApi
import com.example.yolo.data.database.PhotoDatabase
import com.example.yolo.data.database.YoloDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideImageApi(retrofit: Retrofit): PhotoApi = retrofit.create(PhotoApi::class.java)

    @Singleton
    @Provides
    fun getAppDb(context: Application): PhotoDatabase {
        return PhotoDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun getAppDao(photoDatabase: PhotoDatabase): YoloDao {
        return photoDatabase.yoloDao()
    }
}