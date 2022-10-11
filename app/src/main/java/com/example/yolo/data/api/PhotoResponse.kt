package com.example.yolo.data.api

import com.example.yolo.domain.model.unsplash.Photos
import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("results")
    val results: List<Photos>
)