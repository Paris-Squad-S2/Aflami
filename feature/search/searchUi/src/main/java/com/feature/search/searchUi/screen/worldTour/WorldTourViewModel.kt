package com.feature.search.searchUi.screen.worldTour

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.feature.search.searchUi.navigation.Destinations

class WorldTourViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    val args = savedStateHandle.toRoute<Destinations.WorldTourScreen>()

}