package com.feature.lists.listsUi.common

import com.feature.lists.listsUi.navigation.ListNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConcreteBaseViewModel @Inject constructor(
    navigator: ListNavigator
) : BaseViewModel<Unit>(initialState = Unit, navigator = navigator)
