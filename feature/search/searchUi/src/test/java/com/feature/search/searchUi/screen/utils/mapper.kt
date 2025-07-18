package com.feature.search.searchUi.screen.utils

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.domain.search.model.Media
import com.feature.search.searchUi.screen.search.MediaUiState
import com.feature.search.searchUi.screen.search.SearchViewModel.NoopListUpdateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



 suspend fun Flow<PagingData<MediaUiState>>.collectAllItems(): List<MediaUiState> {
    val differ = AsyncPagingDataDiffer(
        diffCallback = object : DiffUtil.ItemCallback<MediaUiState>() {
            override fun areItemsTheSame(oldItem: MediaUiState, newItem: MediaUiState): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MediaUiState, newItem: MediaUiState): Boolean =
                oldItem == newItem
        },
        updateCallback = NoopListUpdateCallback(),
        mainDispatcher = Dispatchers.Main,
        workerDispatcher = Dispatchers.Default
    )

    val job = CoroutineScope(Dispatchers.Main).launch {
        collectLatest { pagingData ->
            differ.submitData(pagingData)
        }
    }

    delay(1000)
    job.cancel()

    return differ.snapshot().items
}

class NoopListUpdateCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int){}
    override fun onRemoved(position: Int, count: Int){}
    override fun onMoved(fromPosition: Int, toPosition: Int){}
    override fun onChanged(position: Int, count: Int, payload: Any?){}
}