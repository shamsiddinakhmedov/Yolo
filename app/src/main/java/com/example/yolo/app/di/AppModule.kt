package com.example.yolo.app.di

import android.content.Context
import com.example.yolo.app.common.Constants
import com.example.yolo.data.api.PhotoApi
import com.example.yolo.data.database.LikedPhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideLikedPhotoDatabase(@ApplicationContext context: Context) =
        LikedPhotoDatabase.getLikePhotoDb(context)

    @Singleton
    @Provides
    fun provideLikedPhotoDao(likedDb: LikedPhotoDatabase) = likedDb.likePhotoDao()
}