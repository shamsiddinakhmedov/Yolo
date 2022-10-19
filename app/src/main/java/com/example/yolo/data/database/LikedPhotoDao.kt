package com.example.yolo.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LikedPhotoDao {
    @Query("SELECT * from liked_photo_db")
    fun getAllLikedPhoto(): Flow<List<LikedPhotoDB>>

    @Insert
    fun insertPhoto(likedPhotoDB: LikedPhotoDB)

    @Delete
    fun deletePhoto(likedPhotoDB: LikedPhotoDB)

}