package com.repository.media.datasource.remote

import com.repository.media.dto.home.MovieListDto
import com.repository.media.dto.home.TvListDto
import com.repository.media.dto.profile.RatedMovieDto
import com.repository.media.dto.profile.RatedTvShowDto

interface MediaRemoteDataSource {
    suspend fun getPopularMovies(language: String): MovieListDto
    suspend fun getTopRatedMovies(language: String): MovieListDto
    suspend fun getUpcomingMovies(language: String): MovieListDto
    suspend fun getNowPlayingMovies(): MovieListDto
    suspend fun getPopularTvShows(language: String): TvListDto
    suspend fun getTopRatedTvShows(language: String): TvListDto
    suspend fun getRatedMovies(accountId: Int,sessionId: String,language: String): RatedMovieDto
    suspend fun getRatedTvShows(accountId: Int,sessionId: String,language: String): RatedTvShowDto
}
