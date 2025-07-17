package com.datasource.remote.tvShow.service

import com.repository.model.remote.TvShowCreditsDto
import com.repository.model.remote.TvShowDto
import com.repository.model.remote.TvShowImagesDto
import com.repository.model.remote.TvShowReviewsDto
import com.repository.model.remote.TvShowSeasonDto
import com.repository.model.remote.TvShowSimilarsDto

interface KtorTvShowDetailsApiService {
    suspend fun getTvShowDetails(tvShowId: Int, language: String): TvShowDto
    suspend fun getTvShowImages(tvShowId: Int): TvShowImagesDto
    suspend fun getTvShowReviews(tvShowId: Int, page: Int, language: String): TvShowReviewsDto
    suspend fun getSimilarTvShows(tvShowId: Int, page: Int, language: String): TvShowSimilarsDto
    suspend fun getTvShowCredits(tvShowId: Int, language: String): TvShowCreditsDto
    suspend fun getSeasonDetails(tvShowId: Int, seasonNumber: Int, language: String): TvShowSeasonDto
}
