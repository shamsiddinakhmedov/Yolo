package com.example.yolo.presentation.view.fragment.photos

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.yolo.app.common.Constants.LATEST
import com.example.yolo.data.repo.PhotoRepository
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseViewModel
import com.example.yolo.presentation.view.fragment.photos.PhotosViewModel.*
import com.example.yolo.presentation.view.fragment.photos.PhotosViewModel.NetworkState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val repository: PhotoRepository
) : BaseViewModel<State, Input, Effect>() {

    data class State(
        val photos: PagingData<Photos>? = null,
        val query: String? = null,
        val orderBy: String? = null,
        val networkState: NetworkState = Idle
    )

    sealed class NetworkState {
        object Empty : NetworkState()
        object Idle : NetworkState()
        object Loading : NetworkState()
        object Error : NetworkState()
    }

    sealed class Input {
        data class SetAttributes(val query: String?, val orderBy: String?) : Input()
        data class LoadStates(val loadStates: CombinedLoadStates, val itemCount: Int) : Input()
        object Retry : Input()
    }

    class Effect

    override fun getInitialState(): State = State()

    override fun processInput(input: Input) {
        when (input) {
            is Input.SetAttributes -> {
                updateState { it.copy(query = input.query, orderBy = input.orderBy) }
            }
            is Input.LoadStates -> setLoadState(input.loadStates, input.itemCount)
            is Input.Retry -> searchPhotos()
        }
    }

    fun searchPhotos() = repository.getSearchResults(
        query = state.value.query ?: "All",
        orderedBy = state.value.orderBy ?: LATEST
    )
        .stateOnEach { state, value ->
            updateState {
                state.copy(photos = value)
            }
        }
        .cachedIn(viewModelScope)
        .launchIn(viewModelScope)

    private fun setLoadState(loadStates: CombinedLoadStates, itemCount: Int) {
        val empty = loadStates.refresh !is LoadState.Loading && itemCount == 0
        val loading = loadStates.refresh is LoadState.Loading && itemCount == 0
        val error = loadStates.refresh is LoadState.Error

        updateState {
            it.copy(
                networkState = when {
                    loading -> Loading
                    error -> Error
                    empty -> Empty
                    else -> Idle
                }
            )
        }
    }
}