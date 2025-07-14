package com.feature.search.searchUi.screen.search

import com.feature.search.searchUi.comon.BaseViewModel

sealed interface SearchUiEffect: BaseViewModel.BaseUiEffect {
    data class NavigateToWorldTourScreen(val name: String = ""): SearchUiEffect
    data class NavigateToFindByActorScreen(val name: String = ""): SearchUiEffect
    data object NavigateToBack: SearchUiEffect
    data class NavigateToMediaDetails(val mediaId: Int): SearchUiEffect
}