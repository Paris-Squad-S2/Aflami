package com.feature.mediaDetails.mediaDetailsUi.screen.tvShow

import com.domain.mediaDetails.useCases.tvShows.AddTvShowToFavoriteUseCase
import com.domain.mediaDetails.useCases.tvShows.AddTvShowToListUseCase
import com.domain.mediaDetails.useCases.tvShows.GetSeasonsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowCastUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowDetailsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowMediaUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowReviewsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowsProductionCompaniesUseCase

class TvShowViewModel(
    private val addTvShowToFavoriteUseCase: AddTvShowToFavoriteUseCase,
    private val addTvShowToListUseCase: AddTvShowToListUseCase,
    private val getSeasonsUseCase : GetSeasonsUseCase,
    private val getTvShowCastUseCase: GetTvShowCastUseCase,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val getTvShowMediaUseCase : GetTvShowMediaUseCase,
    private val getTvShowReviewsUseCase : GetTvShowReviewsUseCase,
    private val getTvShowsProductionCompaniesUseCase : GetTvShowsProductionCompaniesUseCase
) {

}