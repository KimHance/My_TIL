package com.example.uistatewithsealdclass

sealed class State {

    data class Success(
        val name: String = "현수 킴",
    ) : State()

    object Empty : State()

    object Failure : State()

    object Loading : State()
}

