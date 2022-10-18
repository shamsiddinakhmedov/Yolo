package com.example.yolo.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yolo.domain.model.unsplash.Photos

@Entity(tableName = "yolo_table")
data class YoloDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "key")
    var key: Int = 0,

    @ColumnInfo(name = "photoId")
    var photoId: String
)