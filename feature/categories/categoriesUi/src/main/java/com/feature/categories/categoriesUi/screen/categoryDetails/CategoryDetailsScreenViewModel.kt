package com.feature.categories.categoriesUi.screen.categoryDetails

import androidx.lifecycle.viewModelScope
import com.feature.categories.categoriesUi.shared.BaseViewModel
import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.feature.categories.categoriesUi.shared.Status
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.useCase.GetMoviesByCategoryUseCase
import com.paris_2.domain.media.useCase.GetTvShowsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.paris_2.aflami.designsystem.R as RDesignSystem

@HiltViewModel
class CategoryDetailsScreenViewModel @Inject constructor(
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase,
    private val getTvShowsByCategoryUseCase: GetTvShowsByCategoryUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI
) : CategoryDetailsScreenInteractionListener,
    BaseViewModel<CategoryDetailsScreenUIState>(CategoryDetailsScreenUIState()) {

    fun initialCategory(category: CategoryUiState) {
        tryToExecute(
            execute = {
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
            },
            onSuccess = {
                onCategorySelected(category)
            },
            onError = ::handleError,
        )
    }

    override fun onCategorySelected(category: CategoryUiState) {
        reloadAnimation()
        selectCategory(category)
        tryToExecute(
            execute = {
                when (category.type) {
                    MediaType.TvShow -> getTvShowsByCategoryUseCase(
                        category = category.category,
                        page = 1
                    )

                    MediaType.Movie -> getMoviesByCategoryUseCase(
                        category = category.category,
                        page = 1
                    )
                }
            },
            onSuccess = ::handleSuccess,
            onError = ::handleError,
        )
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
                status = Status.Loading
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

    private fun handleSuccess(mediaList: List<Media>) {
        updateState(
            screenState.value.copy(
                categoryDetailsUIState = screenState.value.categoryDetailsUIState.copy(
                    media = mediaList.toMediaUIList(),
                ),
                status = Status.Success
            )
        )
    }

    private fun handleError(error: Throwable) {
        when (error) {
            is NoInternetConnectionException -> {
                updateState(
                    screenState.value.copy(
                        status = Status.NetworkError
                    )
                )
            }
            else -> {
                updateState(
                    screenState.value.copy(
                        status = Status.UnknownError
                    )
                )
            }
        }
    }
}