package com.datasource.remote.mediadetails

import com.datasource.remote.mediadetails.service.KtorTvShowDetailsApiService
import com.example.tvshow.dataSource.remote.TvShowDetailsRemoteDataSource
import com.example.tvshow.model.remote.TvShowCreditsDto
import com.example.tvshow.model.remote.TvShowDto
import com.example.tvshow.model.remote.TvShowImagesDto
import com.example.tvshow.model.remote.TvShowReviewsDto
import com.example.tvshow.model.remote.TvShowSeasonDto
import com.example.tvshow.model.remote.TvShowSimilarsDto

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
