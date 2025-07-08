package com.feature.search.searchUi.screen.filter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.feature.search.searchUi.navigation.Destinations

class FilterViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val args = savedStateHandle.toRoute<Destinations.FilterScreen>()

}