package com.repository.media

import com.repository.media.datasource.remote.MediaRemoteDataSource
import com.repository.media.dto.home.MovieListDto
import com.repository.media.dto.home.TvListDto
import com.repository.media.dto.profile.RatedMovieDto
import com.repository.media.dto.profile.RatedTvShowDto
import com.repository.media.services.MediaApiService
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

    override suspend fun getRatedMovies(accountId: Int,sessionId: String,language: String): RatedMovieDto = apiService.getRatedMovies(accountId, sessionId, language)

    override suspend fun getRatedTvShows(accountId: Int,sessionId: String,language: String): RatedTvShowDto = apiService.getRatedTvShows(accountId, sessionId, language)
}