package com.example.yolo.presentation.view.fragment.main

import com.example.yolo.presentation.architecture.BaseViewModel
import com.example.yolo.presentation.view.fragment.main.MainFragmentViewModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor() : BaseViewModel<State, Input, Effect>() {

    class State
    class Input
    class Effect

    override fun getInitialState(): State = State()

    override fun processInput(input: Input) {
    }
}