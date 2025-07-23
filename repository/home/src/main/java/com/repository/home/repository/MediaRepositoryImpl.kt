package com.repository.home.repository

import android.util.Log
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

    companion object {
        private const val TAG = "MediaRepositoryImpl"
    }

    override suspend fun getPopularMedia(): List<Media> {
        Log.d(TAG, "Fetching popular movies...")
        val popularMovies = mediaRemoteDataSource.getPopularMovies().results?.mapNotNull  {
            it.toDomain(MediaType.MOVIE)
        }?: emptyList()
        Log.d(TAG, "Popular movies fetched: ${popularMovies.size}")

        Log.d(TAG, "Fetching popular TV shows...")
        val popularTvShows = mediaRemoteDataSource.getPopularTvShows().results?.mapNotNull  {
            it.toDomain(MediaType.TV_SHOW)
        }?: emptyList()
        Log.d(TAG, "Popular TV shows fetched: ${popularTvShows.size}")

        val combined = (popularMovies + popularTvShows)
            .sortedByDescending { it.voteAverage }

        Log.d(TAG, "Total popular media combined: ${combined.size}")
        return combined
    }

    override suspend fun getTopRatingMedia(): List<Media> {
        Log.d(TAG, "Fetching top rated movies...")
        val topMovies = mediaRemoteDataSource.getTopRatedMovies().results?.mapNotNull  {
            it.toDomain(MediaType.MOVIE)
        } ?: emptyList()
        Log.d(TAG, "Top rated movies fetched: ${topMovies.size}")

        Log.d(TAG, "Fetching top rated TV shows...")
        val topTv = mediaRemoteDataSource.getTopRatedTvShows().results?.mapNotNull  {
            it.toDomain(MediaType.TV_SHOW)
        } ?: emptyList()
        Log.d(TAG, "Top rated TV shows fetched: ${topTv.size}")

        val combined = (topMovies + topTv)
            .sortedByDescending { it.voteAverage }

        Log.d(TAG, "Total top rated media combined: ${combined.size}")
        return combined
    }

    override suspend fun getUpComingMedia(): List<Media> {
        Log.d(TAG, "Fetching upcoming movies...")
        val upcomingMovies = mediaRemoteDataSource.getUpcomingMovies().results?.mapNotNull  {
            it.toDomain(MediaType.MOVIE)
        } ?: emptyList()
        Log.d(TAG, "Upcoming movies fetched: ${upcomingMovies.size}")
        return upcomingMovies
    }

    override suspend fun getNowPlayingMedia(): List<Media> {
        Log.d(TAG, "Fetching now playing movies...")
        val nowPlaying = mediaRemoteDataSource.getNowPlayingMovies().results?.mapNotNull  {
            it.toDomain(MediaType.MOVIE)
        } ?: emptyList()
        Log.d(TAG, "Now playing movies fetched: ${nowPlaying.size}")
        return nowPlaying
    }

    override suspend fun addMediaToLocal(media: Media) {
        homeMediaLocalDataSource.addMedia(media.toEntity())
    }

    override suspend fun getMediaFromLocal(): List<Media> {
        return homeMediaLocalDataSource.getAllMedia().map { it.toDomain() }
    }
}
