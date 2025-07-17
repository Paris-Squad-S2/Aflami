package com.datasource.remote.tvShow

import com.datasource.remote.tvShow.models.TvShowCreditsDto
import com.datasource.remote.tvShow.models.TvShowDto
import com.datasource.remote.tvShow.models.TvShowImagesDto
import com.datasource.remote.tvShow.models.TvShowReviewsDto
import com.datasource.remote.tvShow.models.TvShowSeasonDto
import com.datasource.remote.tvShow.models.TvShowSimilarsDto
import com.datasource.remote.tvShow.service.KtorTvShowDetailsApiService

class TvShowDetailsRemoteDataSourceImpl(
    private val ktorTvShowDetailsApiService: KtorTvShowDetailsApiService
) : TvShowDetailsRemoteDataSource {

    override suspend fun getTvShowDetails(tvShowId: Int, language: String): TvShowDto {
        return ktorTvShowDetailsApiService.getTvShowDetails(tvShowId, language)
    }

    override suspend fun getTvShowImages(tvShowId: Int): TvShowImagesDto {
        return ktorTvShowDetailsApiService.getTvShowImages(tvShowId)
    }

    override suspend fun getTvShowReviews(tvShowId: Int, page: Int, language: String): TvShowReviewsDto {
        return ktorTvShowDetailsApiService.getTvShowReviews(tvShowId, page, language)
    }

    override suspend fun getSimilarTvShows(tvShowId: Int, page: Int, language: String): TvShowSimilarsDto {
        return ktorTvShowDetailsApiService.getSimilarTvShows(tvShowId, page, language)
    }

    override suspend fun getTvShowCredits(tvShowId: Int, language: String): TvShowCreditsDto {
        return ktorTvShowDetailsApiService.getTvShowCredits(tvShowId, language)
    }

    override suspend fun getSeasonDetails(tvShowId: Int, seasonNumber: Int, language: String): TvShowSeasonDto {
        return ktorTvShowDetailsApiService.getSeasonDetails(tvShowId, seasonNumber, language)
    }
}
