package com.repository.home.datasource.remote

import com.repository.home.dto.MovieListDto
import com.repository.home.dto.TvListDto

interface MediaRemoteDataSource {
    suspend fun getPopularMovies(): MovieListDto
    suspend fun getTopRatedMovies(): MovieListDto
    suspend fun getUpcomingMovies(): MovieListDto
    suspend fun getNowPlayingMovies(): MovieListDto
    suspend fun getPopularTvShows(): TvListDto
    suspend fun getTopRatedTvShows(): TvListDto
}
