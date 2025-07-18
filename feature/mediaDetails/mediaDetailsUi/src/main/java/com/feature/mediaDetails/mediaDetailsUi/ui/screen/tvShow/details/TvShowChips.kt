package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import com.feature.mediaDetails.mediaDetailsUi.R

enum class TvShowChips(val titleResId: Int, val iconResId: Int) {

    SEASONS(
        R.string.seasons,
        com.paris_2.aflami.designsystem.R.drawable.ic_season
    ),

    MORE_LIKE_THIS(
        R.string.more_like_this,
        com.paris_2.aflami.designsystem.R.drawable.ic_camera_video
    ),
    REVIEWS(
        R.string.reviews,
        com.paris_2.aflami.designsystem.R.drawable.ic_starr
    ),
    GALLERY(
        R.string.gallery,
        com.paris_2.aflami.designsystem.R.drawable.ic_album
    ),
    COMPANY_PRODUCTION(
        R.string.company_production,
        com.paris_2.aflami.designsystem.R.drawable.ic_city
    )

}