package com.example.yolo.presentation.view.fragment.likePhotos

import androidx.lifecycle.viewModelScope
import com.example.yolo.data.database.LikedPhotoDB
import com.example.yolo.data.repo.LikedPhotoRepository
import com.example.yolo.presentation.architecture.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.yolo.presentation.view.fragment.likePhotos.LikePhotosViewModel.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LikePhotosViewModel @Inject constructor(
    private val likedPhotoRepository: LikedPhotoRepository
): BaseViewModel<State, Input, Effect>(){

    init {
        getLikedPhotos()
    }
    data class State(
        val likedPhotosList: List<LikedPhotoDB>? = null
    )
    class Input
    class Effect

    override fun getInitialState(): State = State()

    override fun processInput(input: Input) {
    }

    private fun getLikedPhotos() {
        likedPhotoRepository.getAllPhotos()
            .onEach { allPhoto ->
                updateState { it.copy(likedPhotosList = allPhoto) }
            }
            .launchIn(viewModelScope)
    }
}