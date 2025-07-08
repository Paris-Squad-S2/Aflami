package com.feature.search.searchUi.screen.worldTour

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.feature.search.searchUi.navigation.Destinations
import com.feature.search.searchUi.navigation.Navigator
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class WorldTourViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    val args = savedStateHandle.toRoute<Destinations.WorldTourScreen>().name

    private val navigator: Navigator by inject(Navigator::class.java)

    fun onNavigateToSearchScreen() =
        viewModelScope.launch {
            navigator.navigate(Destinations.SearchScreen)
        }
}