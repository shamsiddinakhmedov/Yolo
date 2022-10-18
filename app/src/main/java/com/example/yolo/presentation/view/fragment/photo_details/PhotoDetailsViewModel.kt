package com.example.yolo.presentation.view.fragment.photo_details

import com.example.yolo.data.repo.YoloRepository
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseViewModel
import com.example.yolo.presentation.view.fragment.photo_details.PhotoDetailsViewModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val yoloRepository: YoloRepository
) : BaseViewModel<State, Input, Effect>() {

    data class State(
        val photo: Photos? = null
    )

    sealed class Input {
        data class SetPhoto(val photos: Photos) : Input()
    }

    class Effect

    override fun getInitialState(): State = State()

    override fun processInput(input: Input) {
    }

    suspend fun insertPhoto(photos: Photos) {
        yoloRepository.insertPhoto(photos)
    }
}