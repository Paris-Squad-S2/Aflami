package com.feature.search.searchUi.screen.findByActor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.feature.search.searchUi.navigation.Destinations

class FindByActorViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    val args = savedStateHandle.toRoute<Destinations.FindByActorScreen>()

}