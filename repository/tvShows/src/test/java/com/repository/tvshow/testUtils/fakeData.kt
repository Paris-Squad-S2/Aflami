package com.repository.tvshow.testUtils

import com.repository.model.remote.TvShowCastDto
import com.repository.model.remote.TvShowCreditsDto
import com.repository.model.remote.TvShowDto
import com.repository.model.remote.TvShowImagesDto
import com.repository.model.remote.TvShowLogoDto
import com.repository.model.remote.TvShowProductionCompanyDto
import com.repository.model.remote.TvShowReviewDto
import com.repository.model.remote.TvShowReviewsDto
import com.repository.model.remote.TvShowSimilarDto
import com.repository.model.remote.TvShowSimilarsDto

val mockTvShowDto = TvShowDto(
    id = 550,
    name = "The Epic Blockbuster",
    overview = "Mind-blowing action sequences",
    firstAirDate = "2024-07-15"
)

val mockTvShowCreditsDto = TvShowCreditsDto(
    id = 550,
    cast = listOf(
        TvShowCastDto(
            name = "alex"
        )
    )
)

val mockTvShowSimilarsDto = TvShowSimilarsDto(
    tvShowSimilarDto = listOf(
        TvShowSimilarDto(
            title = "batman"
        )
    )
)

val mockTvShowLogoDto = TvShowImagesDto(
    id = 123,
    logos = listOf(
        TvShowLogoDto(filePath = "http://images.jpeg")
    )
)

val mockTvShowImagesDto = listOf(
    TvShowProductionCompanyDto(
        name = "sonic"
    )
)

val mockTvShowReviewsDto = TvShowReviewsDto(
    results = listOf(
        TvShowReviewDto(
            author = "mohammed"
        )
    )
)