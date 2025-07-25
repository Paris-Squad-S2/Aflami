package com.repository.home.repository

import com.domain.home.exception.AflamiException
import com.domain.home.exception.NoInternetConnectionException
import com.domain.home.exception.NoMediaPlayingFoundException
import com.domain.home.exception.NoPopularMediaFoundException
import com.domain.home.exception.NoTopRatingMediaFoundException
import com.domain.home.exception.NoUpComingMediaFoundException
import com.domain.home.exception.addMediaToLocalException
import com.domain.home.exception.catchMediaFromLocalException
import com.domain.home.model.Media
import com.domain.home.model.MediaType
import com.domain.home.repository.MediaRepository
import com.repository.home.datasource.local.HomeMediaLocalDataSource
import com.repository.home.datasource.remote.MediaRemoteDataSource
import com.repository.home.mapper.toDomain
import com.repository.home.mapper.toEntity
import com.repository.home.util.NetworkConnectionChecker
import com.repository.home.util.detectLanguage
import java.util.Locale

class MediaRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val mediaRemoteDataSource: MediaRemoteDataSource,
    private val homeMediaLocalDataSource: HomeMediaLocalDataSource
) : MediaRepository {
    private val language = detectLanguage()
    override suspend fun getPopularMedia(): List<Media> {

        return safeCall(NoPopularMediaFoundException()) {
            val popularMovies =
                mediaRemoteDataSource.getPopularMovies(language = language).results?.mapNotNull {
                    it.toDomain(MediaType.MOVIE)
                } ?: emptyList()

            val popularTvShows =
                mediaRemoteDataSource.getPopularTvShows(language = language).results?.mapNotNull {
                    it.toDomain(MediaType.TV_SHOW)
                } ?: emptyList()

            val combined = (popularMovies + popularTvShows)
                .sortedByDescending { it.voteAverage }

            combined
        }

    }

    override suspend fun getTopRatingMedia(): List<Media> {
        return safeCall(NoTopRatingMediaFoundException()) {
            val topMovies =
                mediaRemoteDataSource.getTopRatedMovies(language = language).results?.mapNotNull {
                    it.toDomain(MediaType.MOVIE)
                } ?: emptyList()

            val topTv =
                mediaRemoteDataSource.getTopRatedTvShows(language = language).results?.mapNotNull {
                    it.toDomain(MediaType.TV_SHOW)
                } ?: emptyList()

            val combined = (topMovies + topTv)
                .sortedByDescending { it.voteAverage }

            combined
        }
    }

    override suspend fun getUpComingMedia(): List<Media> {
        return safeCall(NoUpComingMediaFoundException()) {
            val upcomingMovies =
                mediaRemoteDataSource.getUpcomingMovies(language = language).results?.mapNotNull {
                    it.toDomain(MediaType.MOVIE)
                } ?: emptyList()
            upcomingMovies
        }

    }

    override suspend fun getNowPlayingMedia(): List<Media> {
        return safeCall(NoMediaPlayingFoundException()) {
            val nowPlaying = mediaRemoteDataSource.getNowPlayingMovies().results?.mapNotNull {
                it.toDomain(MediaType.MOVIE)
            } ?: emptyList()
            nowPlaying
        }
    }

    override suspend fun addMediaToLocal(media: Media) {
        return safeCall(addMediaToLocalException()) {
            homeMediaLocalDataSource.addMedia(media.toEntity())
        }
    }

    override suspend fun getMediaFromLocal(): List<Media> {
        return safeCall(catchMediaFromLocalException()) {
            homeMediaLocalDataSource.getAllMedia().map { it.toDomain() }
        }
    }

    private suspend fun <T> safeCall(exception: AflamiException, call: suspend () -> T): T {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }
        return try {
            call()
        } catch (e: AflamiException) {
            throw e
        } catch (_: Exception) {
            throw exception
        }
    }
}
