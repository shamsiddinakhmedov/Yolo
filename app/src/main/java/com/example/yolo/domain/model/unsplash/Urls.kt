package com.example.yolo.domain.model.unsplash

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Urls(
    @SerializedName("full")
    var full: String,
    @SerializedName("regular")
    var regular: String,
    @SerializedName("small")
    var small: String
) : Parcelable