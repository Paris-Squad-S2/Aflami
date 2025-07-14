package com.domain.mediaDetails.repository

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Season

interface TvShowRepository {
    fun getTvShowDetails(tvShowId: Int): TvShow
    fun getTvShowCast(tvShowId: Int): List<Cast>
    fun getTvShowRecommendations(tvShowId: Int): List<TvShow>
    fun getTvShowGallery(tvShowId: Int): Gallery
    fun getCompanyProducts(tvShowId: Int): List<ProductionCompany>
    fun getTvShowSeasons(tvShowId: Int): List<Season>
    fun getTvShowReview(tvShowId: Int): List<Season>
}