package com.example.yolo.data.database

import androidx.room.TypeConverter
import com.example.yolo.domain.model.unsplash.Photos
import com.google.gson.Gson

class PhotoTypeConverter {

    @TypeConverter
    fun fromPhotos(photos: Photos): String {
        return Gson().toJson(photos)
    }

    @TypeConverter
    fun toPhotos(photos: String): Photos {
        return Gson().fromJson(photos, Photos::class.java)
    }
}