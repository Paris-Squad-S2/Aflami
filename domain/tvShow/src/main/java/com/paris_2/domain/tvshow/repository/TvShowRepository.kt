package com.paris_2.domain.tvshow.repository

import com.paris_2.domain.tvshow.model.TvShowCast
import com.paris_2.domain.tvshow.model.TvShowGallery
import com.paris_2.domain.tvshow.model.TvShowProductionCompany
import com.paris_2.domain.tvshow.model.TvShowReview
import com.paris_2.domain.tvshow.model.Season
import com.paris_2.domain.tvshow.model.TvShow
import com.paris_2.domain.tvshow.model.TvShowSimilar

interface TvShowRepository {
    suspend fun getTvShowDetails(tvShowId: Int): TvShow
    suspend fun getTvShowCast(tvShowId: Int): List<TvShowCast>
    suspend fun getTvShowRecommendations(tvShowId: Int,page: Int): List<TvShowSimilar>
    suspend fun getTvShowGallery(tvShowId: Int): TvShowGallery
    suspend fun getCompanyProducts(tvShowId: Int): List<TvShowProductionCompany>
    suspend fun getSeasonDetails(tvShowId: Int, seasonNumber: Int): Season
    suspend fun getTvShowReview(tvShowId: Int,page: Int): List<TvShowReview>
    suspend fun addTvShowToFavorite(tvShowId: Int)
}