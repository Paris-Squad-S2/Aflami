package com.feature.lists.listsUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.feature.lists.listsApi.ListsDestination
import com.feature.lists.listsApi.ListsFeatureAPI

class ListsFeatureAPIImpl : ListsFeatureAPI {
    override fun invoke(listsDestination: ListsDestination?): @Composable (() -> Unit) {
        return {
            Box(modifier = Modifier.fillMaxSize()) {

            }
        }
    }
}