package com.datasource.remote.mediadetails.service.implementation

import com.datasource.remote.mediadetails.service.KtorTvShowDetailsApiService
import com.datasource.remote.mediadetails.utils.safeApiCall
import com.example.tvshow.model.remote.TvShowCreditsDto
import com.example.tvshow.model.remote.TvShowDto
import com.example.tvshow.model.remote.TvShowImagesDto
import com.example.tvshow.model.remote.TvShowReviewsDto
import com.example.tvshow.model.remote.TvShowSeasonDto
import com.example.tvshow.model.remote.TvShowSimilarsDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class KtorTvShowDetailsApiServiceImpl(
    private val httpClient: HttpClient
) : KtorTvShowDetailsApiService {
    override suspend fun getTvShowDetails(tvShowId: Int, language: String): TvShowDto {
        return safeApiCall {
            httpClient.get(TV_PATH.replace(TV_ID, tvShowId.toString())) {
                parameter(LANGUAGE, language)
            }
        }
    }

    override suspend fun getTvShowImages(tvShowId: Int): TvShowImagesDto {
        return safeApiCall { httpClient.get(TV_PATH.replace(TV_ID, tvShowId.toString()) + IMAGES) }
    }

    override suspend fun getTvShowReviews(tvShowId: Int, page: Int, language: String): TvShowReviewsDto {
        return safeApiCall {
            httpClient.get(TV_PATH.replace(TV_ID, tvShowId.toString()) + REVIEWS) {
                parameter(PAGE, page)
                parameter(LANGUAGE, language)
            }
        }
    }

    override suspend fun getSimilarTvShows(tvShowId: Int, page: Int, language: String): TvShowSimilarsDto {
        return safeApiCall {
            httpClient.get(TV_PATH.replace(TV_ID, tvShowId.toString()) + SIMILAR) {
                parameter(PAGE, page)
                parameter(LANGUAGE, language)
            }
        }
    }

    override suspend fun getTvShowCredits(tvShowId: Int, language: String): TvShowCreditsDto {
        return safeApiCall {
            httpClient.get(TV_PATH.replace(TV_ID, tvShowId.toString()) + CREDITS) {
                parameter(LANGUAGE, language)
            }
        }
    }

    override suspend fun getSeasonDetails(tvShowId: Int, seasonNumber: Int, language: String): TvShowSeasonDto {
        return safeApiCall {
            httpClient.get(TV_PATH.replace(TV_ID, tvShowId.toString()) +
                    SEASON_PATH.replace(SEASON_NUMBER, seasonNumber.toString())) {
                parameter(LANGUAGE, language)
            }
        }
    }

    companion object {
        private const val TV_ID = "{tvShowId}"
        private const val SEASON_NUMBER = "{seasonNumber}"

        private const val TV_PATH = "tv/$TV_ID"
        private const val SEASON_PATH = "/season/$SEASON_NUMBER"

        private const val IMAGES = "/images"
        private const val REVIEWS = "/reviews"
        private const val SIMILAR = "/similar"
        private const val CREDITS = "/credits"

        private const val LANGUAGE = "language"
        private const val PAGE = "page"
    }
}
