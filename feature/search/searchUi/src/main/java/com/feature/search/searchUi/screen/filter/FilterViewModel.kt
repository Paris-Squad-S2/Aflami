package com.feature.search.searchUi.screen.filter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.feature.search.searchUi.navigation.Destinations
import com.feature.search.searchUi.navigation.Navigator
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class FilterViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val args = savedStateHandle.toRoute<Destinations.FilterScreen>().name

    private val navigator: Navigator by inject(Navigator::class.java)

    fun navigateToSearchScreen() =
        viewModelScope.launch {
            navigator.navigate(Destinations.SearchScreen) }


}