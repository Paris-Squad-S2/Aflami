package com.datasource.remote.tvShow.service

import com.repository.model.remote.EpisodeVideoDto
import com.repository.model.remote.TvShowCreditsDto
import com.repository.model.remote.TvShowDto
import com.repository.model.remote.TvShowImagesDto
import com.repository.model.remote.TvShowReviewsDto
import com.repository.model.remote.TvShowSeasonDto
import com.repository.model.remote.TvShowSimilarsDto
import com.repository.model.remote.TvShowVideoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitTvShowDetailsApiService {
    @GET("tv/{series_id}")
    suspend fun getTvShowDetails(
        @Path("series_id") tvShowId: Int,
        @Query("language") language: String
    ): TvShowDto

    @GET("tv/{series_id}/images")
    suspend fun getTvShowImages(@Path("series_id") tvShowId: Int): TvShowImagesDto

    @GET("tv/{series_id}/reviews")
    suspend fun getTvShowReviews(
        @Path("series_id") tvShowId: Int,
        @Query("page") page: Int,
        @Query("language") language: String
    ): TvShowReviewsDto

    @GET("tv/{series_id}/recommendations")
    suspend fun getSimilarTvShows(
        @Path("series_id") tvShowId: Int,
        @Query("page") page: Int,
        @Query("language") language: String
    ): TvShowSimilarsDto

    @GET("tv/{series_id}/credits")
    suspend fun getTvShowCredits(
        @Path("series_id") tvShowId: Int,
        @Query("language") language: String
    ): TvShowCreditsDto

    @GET("tv/{series_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("series_id") tvShowId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("language") language: String
    ): TvShowSeasonDto

    @GET("tv/{series_id}/videos")
    suspend fun getTrailerVideoForTvShow(
        @Path("series_id") tvShowId: Int
    ): TvShowVideoDto

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}/videos")
    suspend fun getTrailerVideoForEpisode(
        @Path("series_id") tvShowId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int,
        @Query("language") language: String
    ): EpisodeVideoDto
}
