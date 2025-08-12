package com.repository.media

import com.repository.media.datasource.remote.MediaRemoteDataSource
import com.repository.media.dto.category.MovieByCategoryDto
import com.repository.media.dto.category.TvShowByCategoryDto
import com.repository.media.dto.home.MovieListDto
import com.repository.media.dto.home.TvListDto
import com.repository.media.dto.profile.RatedMoviesDto
import com.repository.media.dto.profile.RatedTvShowDtoo
import com.repository.media.services.MediaApiService
import javax.inject.Inject

class MediaRemoteDataSourceImpl @Inject constructor(
    private val apiService: MediaApiService
) : MediaRemoteDataSource {

    override suspend fun getPopularMovies(language: String): MovieListDto = apiService.getPopularMovie(language)

    override suspend fun getTopRatedMovies(language: String): MovieListDto = apiService.getTopRatedMovie(language)

    override suspend fun getUpcomingMovies(language: String): MovieListDto = apiService.getUpcoming(language)

    override suspend fun getNowPlayingMovies(): MovieListDto = apiService.getNowPlaying()

    override suspend fun getPopularTvShows(language: String): TvListDto = apiService.getPopularTv(language)

    override suspend fun getTopRatedTvShows(language: String): TvListDto = apiService.getTopRatedTv(language)

    override suspend fun getRatedMovies(accountId: Int,language: String): RatedMoviesDto = apiService.getRatedMovies(accountId, language)
    override suspend fun getRatedTvShows(accountId: Int,language: String): RatedTvShowDtoo = apiService.getRatedTvShows(accountId, language)

    override suspend fun getMoviesByCategory(genreId: Int, page: Int, language: String): MovieByCategoryDto = apiService.getMoviesByCategory(genreId, language, page)

    override suspend fun getTvShowsByCategory(genreId: Int, page: Int, language: String): TvShowByCategoryDto = apiService.getTvShowsByCategory(genreId, language, page)
}