package com.example.yolo.domain.model.unsplash

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Links(
    @SerializedName("download")
    var download: String,
    @SerializedName("download_location")
    var download_location: String
) : Parcelable