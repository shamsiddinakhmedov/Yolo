package com.example.yolo.presentation.view.fragment.photo_details

import androidx.lifecycle.viewModelScope
import com.example.yolo.data.database.LikedPhotoDB
import com.example.yolo.data.repo.LikedPhotoRepository
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseViewModel
import com.example.yolo.presentation.view.fragment.photo_details.PhotoDetailsViewModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val likedPhotoRepository: LikedPhotoRepository
) : BaseViewModel<State, Input, Effect>() {

    init {
        getLikedPhotos()
    }

    data class State(
        val photo: Photos? = null,
        val likedPhotosList: List<LikedPhotoDB>? = null
    )

    sealed class Input {
        data class SetPhoto(val photo: Photos?) : Input()
        object SetLikedPhoto : Input()
    }

    class Effect

    override fun getInitialState(): State = State()

    override fun processInput(input: Input) {
        when (input) {
            is Input.SetPhoto -> {
                updateState { state ->
                    state.copy(photo = input.photo)
                }
            }
            is Input.SetLikedPhoto -> {
                val likedP = LikedPhotoDB(photos = state.value.photo!!)
                if (!state.value.photo!!.liked)
                    viewModelScope.launch { likedPhotoRepository.insertPhoto(likedP) }
                else viewModelScope.launch { likedPhotoRepository.deletePhoto(likedP) }
                updateState { it.copy(photo = state.value.photo!!.copy(liked = !state.value.photo!!.liked)) }
            }
        }
    }

    private fun getLikedPhotos() {
        likedPhotoRepository.getAllPhotos()
            .onEach { allPhoto ->
                updateState { it.copy(likedPhotosList = allPhoto) }
                allPhoto.forEach {
                    if (it.photos.id == state.value.photo?.id)
                        updateState { it.copy(photo = state.value.photo!!.copy(liked = true)) }
                }
            }
            .launchIn(viewModelScope)
    }
}