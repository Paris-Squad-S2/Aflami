package com.domain.user.models

data class UserWatchHistory(
    val movies: List<Media>, val tvShows: List<Media>
)
