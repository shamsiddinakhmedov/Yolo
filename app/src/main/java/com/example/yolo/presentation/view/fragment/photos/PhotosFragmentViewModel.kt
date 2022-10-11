package com.example.yolo.presentation.view.fragment.photos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.yolo.app.common.Constants
import com.example.yolo.data.repo.PhotoRepository
import com.example.yolo.presentation.architecture.BaseViewModel
import com.example.yolo.presentation.view.fragment.photos.PhotosFragmentViewModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosFragmentViewModel @Inject constructor(
    private val repository: PhotoRepository
) : BaseViewModel<State, Input, Effect>() {

    class State
    class Input
    class Effect

    private val currentQuery = MutableLiveData(Constants.DEFAULT_QUERY)
    val photos = currentQuery.switchMap {
        repository.getSearchResults(it).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    override fun getInitialState(): State {
        return State()
    }

    override fun processInput(input: Input) {
    }
}