package com.example.yolo.domain.model.unsplash

import android.os.Parcelable
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photos(
    @SerializedName("photoId")
    var id: String,
    @SerializedName("description")
    var description: String?,
    @SerializedName("color")
    var color: String,
    @SerializedName("urls")
    var urls: Urls,
    @SerializedName("user")
    var user: User,
    @SerializedName("links")
    var links: Links
) : Parcelable