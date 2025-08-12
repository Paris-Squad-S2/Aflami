package com.feature.categories.categoriesUi.shared

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

open class BaseViewModel<S> @Inject constructor(
    initialState: S
) : ViewModel() {

    private val _screenState = MutableStateFlow(initialState)
    val screenState: StateFlow<S> = _screenState.asStateFlow()

    fun updateState(newState: S) {
        _screenState.update { newState }
    }
}