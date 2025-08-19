package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import com.feature.mediaDetails.mediaDetailsUi.R
import com.paris_2.aflami.designsystem.R as RDesignSystem

enum class MovieChips(val titleResId: Int, val iconResId: Int) {
    MORE_LIKE_THIS(
        titleResId = R.string.more_like_this,
        iconResId = RDesignSystem.drawable.ic_camera_video
    ),
    REVIEWS(
        titleResId = R.string.reviews,
        iconResId = RDesignSystem.drawable.ic_starr
    ),
    GALLERY(
        titleResId = R.string.gallery,
        iconResId = RDesignSystem.drawable.ic_album
    ),
    COMPANY_PRODUCTION(
        titleResId = R.string.company_production,
        iconResId = RDesignSystem.drawable.ic_city
    )

}