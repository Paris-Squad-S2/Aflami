package com.datasource.remote.mediadetails

import com.datasource.remote.mediadetails.models.tvShow.TvShowCreditsDto
import com.datasource.remote.mediadetails.models.tvShow.TvShowImagesDto
import com.datasource.remote.mediadetails.models.tvShow.TvShowReviewsDto
import com.datasource.remote.mediadetails.models.tvShow.TvShowSeasonDto
import com.datasource.remote.mediadetails.models.tvShow.TvShowSimilarsDto
import com.datasource.remote.mediadetails.models.tvShow.TvShowDto

interface TvShowDetailsRemoteDataSource {
    suspend fun getTvShowDetails(tvShowId: Int, language: String): TvShowDto
    suspend fun getTvShowImages(tvShowId: Int): TvShowImagesDto
    suspend fun getTvShowReviews(tvShowId: Int, page: Int, language: String): TvShowReviewsDto
    suspend fun getSimilarTvShows(tvShowId: Int, page: Int, language: String): TvShowSimilarsDto
    suspend fun getTvShowCredits(tvShowId: Int, language: String): TvShowCreditsDto
    suspend fun getSeasonDetails(tvShowId: Int, seasonNumber: Int, language: String): TvShowSeasonDto
}
