package com.repository.home.repository

import com.domain.home.model.Media
import com.domain.home.model.MediaType
import com.domain.home.repository.MediaRepository
import com.repository.home.datasource.remote.MediaRemoteDataSource
import com.repository.home.mapper.toDomain

class MediaRepositoryImpl(
    private val mediaRemoteDataSource: MediaRemoteDataSource,

    ): MediaRepository {
    override suspend fun getPopularMedia(): List<Media> {
        val popularMovies = mediaRemoteDataSource.getPopularMovies().results.map {
            it.toDomain(MediaType.MOVIE)
        }
        val popularTvShows = mediaRemoteDataSource.getPopularTvShows().results.map {
            it.toDomain(MediaType.TV_SHOW)
        }

        return (popularMovies + popularTvShows)
            .sortedByDescending { it.voteAverage }
    }

    override suspend fun getTopRatingMedia(): List<Media> {
        val topMovies = mediaRemoteDataSource.getTopRatedMovies().results.map {
            it.toDomain(MediaType.MOVIE)
        }
        val topTv = mediaRemoteDataSource.getTopRatedTvShows().results.map {
            it.toDomain(MediaType.TV_SHOW)
        }

        return (topMovies + topTv)
            .sortedByDescending { it.voteAverage }
    }

    override suspend fun getUpComingMedia(): List<Media> {
        val upcomingMovies = mediaRemoteDataSource.getUpcomingMovies().results.map {
            it.toDomain(MediaType.MOVIE)
        }

        return upcomingMovies
    }

}