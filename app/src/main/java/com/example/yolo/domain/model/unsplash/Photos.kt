package com.example.yolo.domain.model.unsplash

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photos(
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("color")
    val color: String,
    @SerializedName("urls")
    val urls: Urls,
    @SerializedName("user")
    val user: User,
    @SerializedName("links")
    val links: Links
) : Parcelable