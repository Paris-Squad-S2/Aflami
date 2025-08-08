package com.feature.lists.listsUi

import androidx.compose.runtime.Composable
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.lists.listsUi.navigation.ListNavGraph

class ListsFeatureAPIImpl : ListsFeatureAPI {
    override fun invoke(): @Composable (() -> Unit) {
        return {
            ListNavGraph()
        }
    }
}