package com.example.yolo.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.yolo.data.api.PhotoApi
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val photoApi: PhotoApi) {

    fun getSearchResults(query: String) = Pager(
        config = PagingConfig(
            pageSize = 18,
            maxSize = 90,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PhotoPagingSource(photoApi, query)
        }
    ).flow
}