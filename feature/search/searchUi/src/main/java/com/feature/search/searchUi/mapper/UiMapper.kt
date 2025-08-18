package com.feature.search.searchUi.mapper

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.feature.search.searchUi.screen.search.MediaTypeUi
import com.feature.search.searchUi.screen.search.MediaUiState
import com.feature.search.searchUi.screen.search.SearchHistoryUiState
import com.feature.search.searchUi.screen.search.SearchTypeUi
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.entity.SearchHistoryModel
import com.paris_2.domain.media.entity.SearchType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


fun List<MediaUiState>.toDomainList() = this.map { it.toDomainModel() }

fun MediaUiState.toDomainModel(): Media {
    return Media(
        id = this.id,
        imageUri = this.imageUri,
        title = this.title,
        type = this.type.toDomainModel(),
        categories = this.categories,
        yearOfRelease = this.yearOfRelease,
        rating = this.rating
    )
}

fun MediaTypeUi.toDomainModel(): MediaType {
    return when (this) {
        MediaTypeUi.TvShow -> MediaType.TvShow
        MediaTypeUi.Movie -> MediaType.Movie
    }
}

fun List<Media>.toMediaUiList() = this.map { it.toUi() }

fun Media.toUi(): MediaUiState {
    return MediaUiState(
        id = this.id,
        imageUri = this.imageUri,
        title = this.title,
        type = this.type.toUi(),
        categories = this.categories,
        yearOfRelease = this.yearOfRelease,
        rating = this.rating,
    )
}

fun MediaType.toUi(): MediaTypeUi {
    return when (this) {
        MediaType.TvShow -> MediaTypeUi.TvShow
        MediaType.Movie -> MediaTypeUi.Movie
    }
}

fun List<SearchHistoryModel>.toSearchHistoryUiList(): List<SearchHistoryUiState> =
    this.map { it.toSearchHistoryUiState() }

fun SearchHistoryModel.toSearchHistoryUiState(): SearchHistoryUiState {
    return SearchHistoryUiState(
        searchTitle = this.searchTitle,
        searchDate = this.searchDate,
        searchType = searchType.toUi()
    )
}


fun SearchType.toUi(): SearchTypeUi {
    return when (this) {
        SearchType.Query -> SearchTypeUi.Query
        SearchType.Country -> SearchTypeUi.Country
        SearchType.Actor -> SearchTypeUi.Actor
    }
}

fun SearchTypeUi.toDomainModel(): SearchType {
    return when (this) {
        SearchTypeUi.Query -> SearchType.Query
        SearchTypeUi.Country -> SearchType.Country
        SearchTypeUi.Actor -> SearchType.Actor
    }
}

suspend fun Flow<PagingData<Media>>.collectItems(): List<Media> {
    val differ = AsyncPagingDataDiffer(
        diffCallback = object : DiffUtil.ItemCallback<Media>() {
            override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean =
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

suspend fun Flow<PagingData<MediaUiState>>.collectAllItems(): List<MediaUiState> {
    val differ = AsyncPagingDataDiffer(
        diffCallback = object : DiffUtil.ItemCallback<MediaUiState>() {
            override fun areItemsTheSame(
                oldItem: MediaUiState,
                newItem: MediaUiState,
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MediaUiState,
                newItem: MediaUiState,
            ): Boolean =
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
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}
