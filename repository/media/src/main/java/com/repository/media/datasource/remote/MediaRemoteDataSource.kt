package com.repository.media.datasource.remote

import com.repository.media.dto.category.MovieByCategoryDto
import com.repository.media.dto.home.MovieListDto
import com.repository.media.dto.home.TvListDto
import com.repository.media.dto.profile.RatedMoviesDto
import com.repository.media.dto.profile.RatedTvShowDtoo

interface MediaRemoteDataSource {
    suspend fun getPopularMovies(language: String): MovieListDto
    suspend fun getTopRatedMovies(language: String): MovieListDto
    suspend fun getUpcomingMovies(language: String): MovieListDto
    suspend fun getNowPlayingMovies(): MovieListDto
    suspend fun getPopularTvShows(language: String): TvListDto
    suspend fun getTopRatedTvShows(language: String): TvListDto
    suspend fun getRatedMovies(accountId: Int,language: String): RatedMoviesDto
    suspend fun getRatedTvShows(accountId: Int,language: String): RatedTvShowDtoo
    suspend fun getMoviesByCategory(genreId: Int, page: Int , language: String): MovieByCategoryDto
}
