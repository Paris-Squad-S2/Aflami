package com.feature.lists.listsUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.lists.listsUi.navigation.ListNavGraph

class ListsFeatureAPIImpl : ListsFeatureAPI {
    override fun invoke(): @Composable (() -> Unit) {
        return {
            ListNavGraph()
        }
    }
}