package com.domain.home.repository

import com.domain.home.model.BirthdayMedia
import com.domain.home.model.ContinueWatchingMedia
import com.domain.home.model.PopularMedia
import com.domain.home.model.TopRatingMedia
import com.domain.home.model.UpComingMedia

interface MediaRepository {
    suspend fun getPopularMedia() : List<PopularMedia>
    suspend fun getContinueWatchingMedia() : List<ContinueWatchingMedia>
    suspend fun getTopRatingMedia() : List<TopRatingMedia>
    suspend fun getBirthdayMedia() : List<BirthdayMedia>
    suspend fun getUpComingMedia() : List<UpComingMedia>
}