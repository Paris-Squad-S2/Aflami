package com.feature.categories.categoriesUi.screen.categoryDetails

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.feature.categories.categoriesUi.paging.PagingSource
import com.feature.categories.categoriesUi.shared.BaseViewModel
import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.useCase.movie.GetMoviesByCategoryUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.paris.aflami.designsystem.R as RDesignSystem

@HiltViewModel
class CategoryDetailsScreenViewModel @Inject constructor(
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase,
    private val getTvShowsByCategoryUseCase: GetTvShowsByCategoryUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI
) : CategoryDetailsScreenInteractionListener,
    BaseViewModel<CategoryDetailsScreenUIState>(CategoryDetailsScreenUIState()) {

    fun initialCategory(category: CategoryUiState) {
        updateState(
            screenState.value.copy(
                categoryDetailsUIState = screenState.value.categoryDetailsUIState.copy(
                    selectedCategory = category,
                    title = when (category.type) {
                        MediaType.TvShow -> RDesignSystem.string.tv_shows
                        MediaType.Movie -> RDesignSystem.string.movies
                    },
                    categories = when (category.type) {
                        MediaType.TvShow -> CategoryUiState.getTvShowsCategories()
                        MediaType.Movie -> CategoryUiState.getMoviesCategories()
                    },
                ),
            )
        )
        onCategorySelected(category)
    }

    override fun onCategorySelected(category: CategoryUiState) {
        reloadAnimation()
        selectCategory(category)
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = {
                    PagingSource(
                        mediaUseCase = { page ->
                            when (category.type) {
                                MediaType.TvShow -> getTvShowsByCategoryUseCase(
                                    category = category.category,
                                    page = page
                                ).toMediaUIList()

                                MediaType.Movie -> getMoviesByCategoryUseCase(
                                    category = category.category,
                                    page = page
                                ).toMediaUIList()
                            }
                        }
                    )
                }
            ).flow.cachedIn(viewModelScope).also {
                handleSuccess(it)
            }
        }
    }

    private fun selectCategory(category: CategoryUiState) {
        updateState(
            screenState.value.copy(
                categoryDetailsUIState = screenState.value.categoryDetailsUIState.copy(
                    selectedCategory = category,
                ),
            )
        )
    }

    override fun onRetry() {
        onCategorySelected(category = screenState.value.categoryDetailsUIState.selectedCategory)
    }

    override fun onMediaSelected(media: MediaUI) {
        when (media.type) {
            MediaType.TvShow -> mediaDetailsFeatureAPI.startTvShowDetails(
                tvShowId = media.id,
            )

            MediaType.Movie -> mediaDetailsFeatureAPI.startMovieDetails(
                movieId = media.id,
            )
        }
    }

    private fun reloadAnimation() {
        updateState(
            screenState.value.copy(
                categoryDetailsUIState = screenState.value.categoryDetailsUIState.copy(
                    mediaVisibility = false
                ),
            )
        )
        viewModelScope.launch {
            delay(200)
            updateState(
                screenState.value.copy(
                    categoryDetailsUIState = screenState.value.categoryDetailsUIState.copy(
                        mediaVisibility = true
                    ),
                )
            )
        }
    }

    private fun handleSuccess(mediaList: Flow<PagingData<MediaUI>>) {
        updateState(
            screenState.value.copy(
                categoryDetailsUIState = screenState.value.categoryDetailsUIState.copy(
                    media = mediaList,
                ),
            )
        )
    }
}