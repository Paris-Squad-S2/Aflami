package com.repository.media.repository

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.exception.AddMediaToContinueWatchingException
import com.paris_2.domain.media.exception.AflamiException
import com.paris_2.domain.media.exception.GetContinueWatchingMediaException
import com.paris_2.domain.media.exception.MediaPlayingException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.exception.NoRatedMediaFoundException
import com.paris_2.domain.media.exception.PopularMediaException
import com.paris_2.domain.media.exception.TopRatingMediaException
import com.paris_2.domain.media.exception.UpComingMediaException
import com.paris_2.domain.media.repository.MediaRepository
import com.repository.media.datasource.local.ContinueWatchingLocalDataSource
import com.repository.media.datasource.local.HomeMediaLocalDataSource
import com.repository.media.datasource.remote.MediaRemoteDataSource
import com.repository.media.entity.Category
import com.repository.media.mapper.toDomain
import com.repository.media.mapper.toEntity
import com.repository.media.mapper.toMediaEntity
import com.repository.media.util.NetworkConnectionChecker
import com.repository.media.util.detectLanguage

class MediaRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val mediaRemoteDataSource: MediaRemoteDataSource,
    private val continueWatchingLocalDataSource: ContinueWatchingLocalDataSource,
    private val homeMediaLocalDataSource: HomeMediaLocalDataSource
) : MediaRepository {
    private val language = detectLanguage()
    override suspend fun getPopularMedia(): List<Media> {

        val localMedia = homeMediaLocalDataSource.getMediaListByCategory(Category.POPULAR)
        if (localMedia.isNotEmpty()) return localMedia.mapNotNull { it.toDomain() }

        return safeCall(PopularMediaException()) {
            val remoteMovies = mediaRemoteDataSource.getPopularMovies(language).results?.mapNotNull {
                it.toDomain(MediaType.MOVIE)
            } ?: emptyList()

            val remoteTvShows = mediaRemoteDataSource.getPopularTvShows(language).results?.mapNotNull {
                it.toDomain(MediaType.TVSHOW)
            } ?: emptyList()

            val combined = (remoteMovies + remoteTvShows).sortedByDescending { it.rating }

            val entities = combined.map {
                it.toMediaEntity(category = Category.POPULAR)
            }

            homeMediaLocalDataSource.addMediaList(entities)
            combined
        }
    }
    override suspend fun getTopRatingMedia(): List<Media> {
        val localMedia = homeMediaLocalDataSource.getMediaListByCategory(Category.TOP_RATED)

        if (localMedia.isNotEmpty()) return localMedia.mapNotNull { it.toDomain() }

        return safeCall(TopRatingMediaException()) {

            val remoteMovies = mediaRemoteDataSource.getTopRatedMovies(language).results?.mapNotNull {
                it.toDomain(MediaType.MOVIE)
            } ?: emptyList()

            val remoteTv = mediaRemoteDataSource.getTopRatedTvShows(language).results?.mapNotNull {
                it.toDomain(MediaType.TVSHOW)
            } ?: emptyList()

            val combined = (remoteMovies + remoteTv).sortedByDescending { it.rating }

            val entities = combined.map {
                it.toMediaEntity(category = Category.TOP_RATED)
            }
            homeMediaLocalDataSource.addMediaList(entities)
            combined
        }
    }

    override suspend fun getUpComingMedia(): List<Media> {
        val localMedia = homeMediaLocalDataSource.getMediaListByCategory(Category.UPCOMING)
        if (localMedia.isNotEmpty()) return localMedia.mapNotNull { it.toDomain() }

        return safeCall(UpComingMediaException()) {
            val upcomingMovies = mediaRemoteDataSource.getUpcomingMovies(language = language)
                .results?.mapNotNull {
                    it.toDomain(MediaType.MOVIE)
                } ?: emptyList()
            val entities = upcomingMovies.map {
                it.toMediaEntity(category = Category.UPCOMING)
            }
            homeMediaLocalDataSource.addMediaList(entities)
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
            continueWatchingLocalDataSource.addMedia(media.toEntity())
        }
    }

    override suspend fun getContinueWatchingMedia(): List<Media> {
        return safeCall(GetContinueWatchingMediaException()) {
            continueWatchingLocalDataSource.getAllMedia().map { it.toDomain() }
        }
    }

    override suspend fun getRatedMedia(accountId: Int,sessionId: String): List<Media> {
        return safeCall(NoRatedMediaFoundException()) {
            val ratedMovies = mediaRemoteDataSource.getRatedMovies(accountId, sessionId, language)
                .results.mapNotNull { it.toDomain(MediaType.MOVIE) }

            val ratedTvShows = mediaRemoteDataSource.getRatedTvShows(accountId, sessionId, language)
                .results.mapNotNull {
                    it.toDomain(MediaType.TVSHOW)
                }
            ratedMovies + ratedTvShows
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
