package com.example.uistatewithsealdclass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.Empty)
    val uiState = _uiState.asStateFlow()

    fun doSuccess() {
        viewModelScope.launch {
            runCatching {
                _uiState.value = State.Loading
                delay(2000L)
            }.onSuccess {
                _uiState.value = State.Success()
            }
        }
    }

    fun doFailed() {
        viewModelScope.launch {
            runCatching {
                _uiState.value = State.Loading
                delay(2000L)
                throw Error("강제 실패 발생")
            }.onFailure {
                _uiState.value = State.Failure
            }
        }
    }

    fun reset() {
        viewModelScope.launch {
            _uiState.value = State.Empty
        }
    }
}