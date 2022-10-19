package com.example.yolo.data.repo

import com.example.yolo.data.database.LikedPhotoDB
import com.example.yolo.data.database.LikedPhotoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class LikedPhotoRepository @Inject constructor(private val dao: LikedPhotoDao) {

    fun getAllPhotos(): Flow<List<LikedPhotoDB>> = dao.getAllLikedPhoto().distinctUntilChanged()

    fun insertPhoto(likedPhotoDB: LikedPhotoDB) = dao.insertPhoto(likedPhotoDB)

    fun deletePhoto(likedPhotoDB: LikedPhotoDB) = dao.deletePhoto(likedPhotoDB)
}