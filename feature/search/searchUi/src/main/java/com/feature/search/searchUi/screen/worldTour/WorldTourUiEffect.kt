package com.feature.search.searchUi.screen.worldTour

import com.feature.search.searchUi.comon.BaseViewModel

sealed interface WorldTourUiEffect: BaseViewModel.BaseUiEffect {
    data object NavigateToBack: WorldTourUiEffect
}