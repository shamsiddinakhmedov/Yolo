package com.example.yolo.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.yolo.data.api.PhotoApi
import com.example.yolo.domain.model.unsplash.Photos

class PhotoPagingSource(
    private val photoApi: PhotoApi,
    private val query: String,
    private val order: String
) : PagingSource<Int, Photos>() {

    override fun getRefreshKey(state: PagingState<Int, Photos>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photos> = try {
        val page = params.key ?: 1
        val response = photoApi.getImage(
            query = query,
            page = page,
            perPage = params.loadSize,
            order_by = order
        )
        val photos = response.results
        LoadResult.Page(
            data = photos,
            prevKey = params.key?.takeIf { it > 1 }?.let { it - 1 },
            nextKey = if (photos.isNotEmpty()) page + 1 else null
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}