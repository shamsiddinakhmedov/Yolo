package com.example.yolo.presentation.view.main

import com.example.yolo.presentation.architecture.BaseViewModel
import com.example.yolo.presentation.view.main.MainViewModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<State, Input, Effect>() {

    class State

    sealed class Input {
    }

    sealed class Effect

    override fun getInitialState(): State {
        return State()
    }

    override fun processInput(input: Input) {
    }
}