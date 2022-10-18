package com.example.yolo.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.yolo.domain.model.unsplash.Photos

@Dao
interface YoloDao {
    @Query("SELECT * from yolo_table")
    suspend fun getAllLikedPhoto(): List<String>

    @Insert
    suspend fun insertPhoto(photoId: String)

    @Delete
    suspend fun deletePhoto(photoId: String)

}