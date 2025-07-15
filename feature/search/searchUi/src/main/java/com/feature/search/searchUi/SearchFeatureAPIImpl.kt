package com.feature.search.searchUi

import androidx.compose.runtime.Composable
import com.feature.search.searchApi.SearchDestination
import com.feature.search.searchApi.SearchFeatureAPI
import com.feature.search.searchUi.navigation.SearchNavGraph

class SearchFeatureAPIImpl : SearchFeatureAPI {
    override operator fun invoke(searchDestination: SearchDestination?): @Composable () -> Unit = {
        SearchNavGraph(startDestination = searchDestination)
    }
}