package com.example.yolo.presentation.view.fragment.folder

import com.example.yolo.data.repo.PhotoRepository
import com.example.yolo.presentation.architecture.BaseViewModel
import com.example.yolo.presentation.view.fragment.folder.FolderFragmentViewModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderFragmentViewModel @Inject constructor(
    private val repository: PhotoRepository
) : BaseViewModel<State, Input, Effect>() {

    class State
    class Input
    class Effect

    override fun getInitialState(): State = State()

    override fun processInput(input: Input) {
    }

}