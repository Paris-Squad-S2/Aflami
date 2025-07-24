package com.repository.home.repository

import com.domain.home.model.Media
import com.domain.home.model.MediaType
import com.domain.home.repository.MediaRepository
import com.repository.home.datasource.local.HomeMediaLocalDataSource
import com.repository.home.datasource.remote.MediaRemoteDataSource
import com.repository.home.mapper.toDomain
import com.repository.home.mapper.toEntity

class MediaRepositoryImpl(
    private val mediaRemoteDataSource: MediaRemoteDataSource,
    private val homeMediaLocalDataSource: HomeMediaLocalDataSource
) : MediaRepository {

    override suspend fun getPopularMedia(): List<Media> {
        val popularMovies = mediaRemoteDataSource.getPopularMovies().results?.mapNotNull {
            it.toDomain(MediaType.MOVIE)
        } ?: emptyList()

        val popularTvShows = mediaRemoteDataSource.getPopularTvShows().results?.mapNotNull {
            it.toDomain(MediaType.TV_SHOW)
        } ?: emptyList()

        val combined = (popularMovies + popularTvShows)
            .sortedByDescending { it.voteAverage }

        return combined
    }

    override suspend fun getTopRatingMedia(): List<Media> {
        val topMovies = mediaRemoteDataSource.getTopRatedMovies().results?.mapNotNull {
            it.toDomain(MediaType.MOVIE)
        } ?: emptyList()

        val topTv = mediaRemoteDataSource.getTopRatedTvShows().results?.mapNotNull {
            it.toDomain(MediaType.TV_SHOW)
        } ?: emptyList()

        val combined = (topMovies + topTv)
            .sortedByDescending { it.voteAverage }

        return combined
    }

    override suspend fun getUpComingMedia(): List<Media> {
        val upcomingMovies = mediaRemoteDataSource.getUpcomingMovies().results?.mapNotNull {
            it.toDomain(MediaType.MOVIE)
        } ?: emptyList()
        return upcomingMovies
    }

    override suspend fun getNowPlayingMedia(): List<Media> {
        val nowPlaying = mediaRemoteDataSource.getNowPlayingMovies().results?.mapNotNull {
            it.toDomain(MediaType.MOVIE)
        } ?: emptyList()
        return nowPlaying
    }

    override suspend fun addMediaToLocal(media: Media) {
        homeMediaLocalDataSource.addMedia(media.toEntity())
    }

    override suspend fun getMediaFromLocal(): List<Media> {
        return homeMediaLocalDataSource.getAllMedia().map { it.toDomain() }
    }
}
