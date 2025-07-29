package com.feature.home.homeUi.common

import com.feature.home.homeUi.navigation.HomeNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConcreteBaseViewModel @Inject constructor(
    navigator: HomeNavigator
) : BaseViewModel<Unit>(initialState = Unit, navigator = navigator)
