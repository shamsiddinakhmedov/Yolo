package com.example.yolo.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yolo.domain.model.unsplash.Photos

@Entity(tableName = "liked_photo_db")
data class LikedPhotoDB(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "photos")
    var photos: Photos
)