package com.example.yolo.presentation.view.fragment.photos

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.yolo.data.repo.PhotoRepository
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseViewModel
import com.example.yolo.presentation.view.fragment.photos.PhotosFragmentViewModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class PhotosFragmentViewModel @Inject constructor(
    private val repository: PhotoRepository
) : BaseViewModel<State, Input, Effect>() {

    data class State(
        val photos: PagingData<Photos>? = null
    )

    class Input
    class Effect

    override fun getInitialState(): State = State()

    override fun processInput(input: Input) {
    }

    fun searchPhotos(query: String) = repository.getSearchResults(query)
        .cachedIn(viewModelScope)
        .stateOnEach { state, value ->
            state.copy(photos = value)
        }.launchIn(viewModelScope)

}