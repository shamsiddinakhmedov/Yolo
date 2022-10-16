package com.example.yolo.domain.database

import androidx.room.Dao
import androidx.room.Query
import com.example.yolo.domain.model.unsplash.Photos

@Dao
interface YoloDao {
    @Query("SELECT * from YoloDb WHERE `like` like 1")
    fun getAllLikedPhoto(): List<Photos>
}