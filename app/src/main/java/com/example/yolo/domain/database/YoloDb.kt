package com.example.yolo.domain.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yolo.domain.model.unsplash.Photos

@Entity
data class YoloDb(
    @PrimaryKey val photoId: String,
    @ColumnInfo(name = "photo") val photos: Photos,
    @ColumnInfo(name = "like") val like: Int
)