package com.repository.home.repository

import com.paris_2.domain.media.exception.AflamiException
import com.paris_2.domain.media.exception.AddMediaToContinueWatchingException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.exception.MediaPlayingException
import com.paris_2.domain.media.exception.PopularMediaException
import com.paris_2.domain.media.exception.TopRatingMediaException
import com.paris_2.domain.media.exception.GetContinueWatchingMediaException
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.exception.UpComingMediaException
import com.paris_2.domain.media.repository.MediaRepository
import com.repository.home.datasource.local.HomeMediaLocalDataSource
import com.repository.home.datasource.remote.MediaRemoteDataSource
import com.repository.home.mapper.toDomain
import com.repository.home.mapper.toEntity
import com.repository.home.util.NetworkConnectionChecker
import com.repository.home.util.detectLanguage

class MediaRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val mediaRemoteDataSource: MediaRemoteDataSource,
    private val homeMediaLocalDataSource: HomeMediaLocalDataSource
) : MediaRepository {
    private val language = detectLanguage()
    override suspend fun getPopularMedia(): List<Media> {

        return safeCall(PopularMediaException()) {
            val popularMovies =
                mediaRemoteDataSource.getPopularMovies(language = language).results?.mapNotNull {
                    it.toDomain(MediaType.MOVIE)
                } ?: emptyList()

            val popularTvShows =
                mediaRemoteDataSource.getPopularTvShows(language = language).results?.mapNotNull {
                    it.toDomain(MediaType.TVSHOW)
                } ?: emptyList()

            val combined = (popularMovies + popularTvShows)
                .sortedByDescending { it.rating }

            combined
        }

    }

    override suspend fun getTopRatingMedia(): List<Media> {
        return safeCall(TopRatingMediaException()) {
            val topMovies =
                mediaRemoteDataSource.getTopRatedMovies(language = language).results?.mapNotNull {
                    it.toDomain(MediaType.MOVIE)
                } ?: emptyList()

            val topTv =
                mediaRemoteDataSource.getTopRatedTvShows(language = language).results?.mapNotNull {
                    it.toDomain(MediaType.TVSHOW)
                } ?: emptyList()

            val combined = (topMovies + topTv)
                .sortedByDescending { it.rating }

            combined
        }
    }

    override suspend fun getUpComingMedia(): List<Media> {
        return safeCall(UpComingMediaException()) {
            val upcomingMovies =
                mediaRemoteDataSource.getUpcomingMovies(language = language).results?.mapNotNull {
                    it.toDomain(MediaType.MOVIE)
                } ?: emptyList()
            upcomingMovies
        }

    }

    override suspend fun getNowPlayingMedia(): List<Media> {
        return safeCall(MediaPlayingException()) {
            val nowPlaying = mediaRemoteDataSource.getNowPlayingMovies().results?.mapNotNull {
                it.toDomain(MediaType.MOVIE)
            } ?: emptyList()
            nowPlaying
        }
    }

    override suspend fun addMediaToContinueWatching(media: Media) {
        return safeCall(AddMediaToContinueWatchingException()){
            homeMediaLocalDataSource.addMedia(media.toEntity())
        }
    }

    override suspend fun getMediaFromLocal(): List<Media> {
        return safeCall(GetContinueWatchingMediaException()) {
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
