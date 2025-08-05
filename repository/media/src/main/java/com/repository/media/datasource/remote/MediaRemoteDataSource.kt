package com.repository.media.datasource.remote

import com.repository.media.dto.home.MovieListDto
import com.repository.media.dto.home.TvListDto

interface MediaRemoteDataSource {
    suspend fun getPopularMovies(language: String): MovieListDto
    suspend fun getTopRatedMovies(language: String): MovieListDto
    suspend fun getUpcomingMovies(language: String): MovieListDto
    suspend fun getNowPlayingMovies(): MovieListDto
    suspend fun getPopularTvShows(language: String): TvListDto
    suspend fun getTopRatedTvShows(language: String): TvListDto
}
