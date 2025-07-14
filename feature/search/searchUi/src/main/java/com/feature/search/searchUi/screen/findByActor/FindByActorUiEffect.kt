package com.feature.search.searchUi.screen.findByActor

import com.feature.search.searchUi.comon.BaseViewModel

sealed interface FindByActorUiEffect: BaseViewModel.BaseUiEffect {
    data object NavigateToBack: FindByActorUiEffect
}