package com.datasource.remote.tvShow

import com.datasource.remote.tvShow.models.TvShowCreditsDto
import com.datasource.remote.tvShow.models.TvShowDto
import com.datasource.remote.tvShow.models.TvShowImagesDto
import com.datasource.remote.tvShow.models.TvShowReviewsDto
import com.datasource.remote.tvShow.models.TvShowSeasonDto
import com.datasource.remote.tvShow.models.TvShowSimilarsDto

interface TvShowDetailsRemoteDataSource {
    suspend fun getTvShowDetails(tvShowId: Int, language: String): TvShowDto
    suspend fun getTvShowImages(tvShowId: Int): TvShowImagesDto
    suspend fun getTvShowReviews(tvShowId: Int, page: Int, language: String): TvShowReviewsDto
    suspend fun getSimilarTvShows(tvShowId: Int, page: Int, language: String): TvShowSimilarsDto
    suspend fun getTvShowCredits(tvShowId: Int, language: String): TvShowCreditsDto
    suspend fun getSeasonDetails(tvShowId: Int, seasonNumber: Int, language: String): TvShowSeasonDto
}
