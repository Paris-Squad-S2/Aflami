package com.feature.search.searchUi.screen.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.feature.search.searchUi.navigation.Destinations

class SearchViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    val args = savedStateHandle.toRoute<Destinations.SearchScreen>()

}