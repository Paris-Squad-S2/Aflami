package com.domain.mediaDetails.repository

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.model.Season

interface TvShowRepository {
    suspend fun getTvShowDetails(tvShowId: Int): TvShow
    suspend fun getTvShowCast(tvShowId: Int): List<Cast>
    suspend fun getTvShowRecommendations(tvShowId: Int,page: Int): List<TvShow>
    suspend fun getTvShowGallery(tvShowId: Int): Gallery
    suspend fun getCompanyProducts(tvShowId: Int): List<ProductionCompany>
    suspend fun getSeasonDetails(tvShowId: Int, seasonNumber: Int): Season
    suspend fun getTvShowReview(tvShowId: Int,page: Int): List<Review>
    suspend fun addTvShowToFavorite(tvShowId: Int)
}