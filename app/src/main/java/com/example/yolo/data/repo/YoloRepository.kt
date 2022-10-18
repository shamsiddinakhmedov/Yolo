package com.example.yolo.data.repo

import com.example.yolo.data.database.YoloDao
import com.example.yolo.domain.model.unsplash.Photos
import javax.inject.Inject

class YoloRepository @Inject constructor(private val dao: YoloDao) {

    suspend fun getAllPhotos() = dao.getAllLikedPhoto()
    suspend fun insertPhoto(photos: Photos) = dao.insertPhoto(photos)
    suspend fun deletePhoto(photos: Photos) = dao.deletePhoto(photos)
}