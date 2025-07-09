package com.feature.search.searchUi.screen.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.feature.search.searchUi.navigation.Destinations
import com.feature.search.searchUi.navigation.Navigator
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class SearchViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val navigator: Navigator by inject(Navigator::class.java)

    fun onNavigateToFilterScreen(string: String) =
        viewModelScope.launch {
            navigator.navigate(Destinations.FilterScreen(string))
        }

    fun onNavigateToWorldTourScreen(string: String) =
        viewModelScope.launch {
            navigator.navigate(Destinations.WorldTourScreen(
                name = string
            ))
        }
    fun onNavigateToFindByActorScreen(string: String) =
        viewModelScope.launch {
            navigator.navigate(Destinations.FindByActorScreen(
                name = string
            ))
        }
}