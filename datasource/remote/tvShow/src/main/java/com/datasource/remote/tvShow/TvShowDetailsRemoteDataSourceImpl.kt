package com.datasource.remote.tvShow

import com.datasource.remote.tvShow.service.KtorTvShowDetailsApiService
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.model.remote.TvShowCreditsDto
import com.repository.model.remote.TvShowDto
import com.repository.model.remote.TvShowImagesDto
import com.repository.model.remote.TvShowReviewsDto
import com.repository.model.remote.TvShowSeasonDto
import com.repository.model.remote.TvShowSimilarsDto

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
