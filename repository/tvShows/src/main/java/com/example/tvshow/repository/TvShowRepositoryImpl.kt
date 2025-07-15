package com.example.tvshow.repository

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.model.Season
import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.repository.TvShowRepository

class TvShowRepositoryImpl(
    // inject here local and remote data source dependency
) : TvShowRepository {
    override suspend fun getTvShowDetails(tvShowId: Int): TvShow {
        TODO("Not yet implemented")
    }

    override suspend fun getTvShowCast(tvShowId: Int): List<Cast> {
        TODO("Not yet implemented")
    }

    override suspend fun getTvShowRecommendations(tvShowId: Int): List<TvShow> {
        TODO("Not yet implemented")
    }

    override suspend fun getTvShowGallery(tvShowId: Int): Gallery {
        TODO("Not yet implemented")
    }

    override suspend fun getCompanyProducts(tvShowId: Int): List<ProductionCompany> {
        TODO("Not yet implemented")
    }

    override suspend fun getTvShowSeasons(tvShowId: Int): List<Season> {
        TODO("Not yet implemented")
    }

    override suspend fun getTvShowReview(tvShowId: Int): List<Review> {
        TODO("Not yet implemented")
    }

    override suspend fun addTvShowToFavorite(tvShowId: Int) {
        TODO("Not yet implemented")
    }

}