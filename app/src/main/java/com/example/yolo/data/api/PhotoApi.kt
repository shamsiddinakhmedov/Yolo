package com.example.yolo.data.api

import com.example.yolo.app.common.Constants.KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PhotoApi {
    @Headers("Accept-Version: v1", "Authorization: Client-ID $KEY")
    @GET("search/photos")
    suspend fun getImage(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PhotoResponse
}