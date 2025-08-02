package com.repository.home

import com.repository.media.datasource.remote.MediaRemoteDataSource
import com.repository.media.dto.MovieListDto
import com.repository.media.dto.TvListDto
import javax.inject.Inject

class MediaDataSourceImpl @Inject constructor(
    private val apiService: MediaApiService
) : MediaRemoteDataSource {

    override suspend fun getPopularMovies(language: String): MovieListDto = apiService.getPopularMovie(language)

    override suspend fun getTopRatedMovies(language: String): MovieListDto = apiService.getTopRatedMovie(language)

    override suspend fun getUpcomingMovies(language: String): MovieListDto = apiService.getUpcoming(language)

    override suspend fun getNowPlayingMovies(): MovieListDto = apiService.getNowPlaying()

    override suspend fun getPopularTvShows(language: String): TvListDto = apiService.getPopularTv(language)

    override suspend fun getTopRatedTvShows(language: String): TvListDto = apiService.getTopRatedTv(language)
}