package com.repository.home.datasource.remote

import com.repository.home.dto.MovieListDto
import com.repository.home.dto.TvListDto

interface MediaRemoteDataSource {
    suspend fun getPopularMovies(language: String): MovieListDto
    suspend fun getTopRatedMovies(language: String): MovieListDto
    suspend fun getUpcomingMovies(language: String): MovieListDto
    suspend fun getNowPlayingMovies(): MovieListDto
    suspend fun getPopularTvShows(language: String): TvListDto
    suspend fun getTopRatedTvShows(language: String): TvListDto
}
