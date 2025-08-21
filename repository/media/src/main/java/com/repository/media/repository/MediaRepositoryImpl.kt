package com.repository.media.repository

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.exception.AflamiException
import com.paris_2.domain.media.exception.FailedException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.repository.MediaRepository
import com.paris_2.domain.media.repository.MovieRepository
import com.paris_2.domain.media.repository.TvShowRepository
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.media.datasource.local.MediaLocalDataSource
import com.repository.media.datasource.remote.MediaRemoteDataSource
import com.repository.media.mapper.toDomain
import com.repository.media.mapper.toEntity
import com.repository.media.mapper.toId
import com.repository.media.mapper.toMedia
import com.repository.media.mapper.toMediaEntity
import com.repository.media.models.local.media.Category
import com.repository.media.models.local.media.MediaTypeEntity
import com.repository.media.util.NetworkConnectionChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MediaRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val mediaRemoteDataSource: MediaRemoteDataSource,
    private val mediaLocalDataSource: MediaLocalDataSource,
    private val settingLocalDataSource: SettingLocalDataSource,
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) : MediaRepository {

    override suspend fun getPopularMedia(): List<Media> {
        val language = settingLocalDataSource.getLanguage().first()
        val localMedia = mediaLocalDataSource.getHomeMediaByCategory(Category.POPULAR, language)
        if (localMedia.isNotEmpty()) return localMedia.mapNotNull { it.toDomain() }

        return safeCall(FailedException("getPopularMedia")) {
            val remoteMovies =
                mediaRemoteDataSource.getPopularMovies(language).results?.mapNotNull {
                    it.toDomain(MediaType.Movie)
                } ?: emptyList()

            val remoteTvShows =
                mediaRemoteDataSource.getPopularTvShows(language).results?.mapNotNull {
                    it.toDomain(MediaType.TvShow)
                } ?: emptyList()

            val combined = (remoteMovies + remoteTvShows).sortedByDescending { it.rating }

            val entities = combined.map {
                it.toMediaEntity(category = Category.POPULAR, language)
            }

            mediaLocalDataSource.addHomeMedia(entities)
            combined
        }
    }

    override suspend fun getTopRatingMedia(): List<Media> {
        val language = settingLocalDataSource.getLanguage().first()
        val localMedia =
            mediaLocalDataSource.getHomeMediaByCategory(Category.TOP_RATED, language)

        if (localMedia.isNotEmpty()) return localMedia.mapNotNull { it.toDomain() }

        return safeCall(FailedException("getTopRatingMedia")) {

            val remoteMovies =
                mediaRemoteDataSource.getTopRatedMovies(language).results?.mapNotNull {
                    it.toDomain(MediaType.Movie)
                } ?: emptyList()

            val remoteTv = mediaRemoteDataSource.getTopRatedTvShows(language).results?.mapNotNull {
                it.toDomain(MediaType.TvShow)
            } ?: emptyList()

            val combined = (remoteMovies + remoteTv).sortedByDescending { it.rating }

            val entities = combined.map {
                it.toMediaEntity(category = Category.TOP_RATED, language)
            }
            mediaLocalDataSource.addHomeMedia(entities)
            combined
        }
    }

    override suspend fun getUpComingMedia(): List<Media> {
        val language = settingLocalDataSource.getLanguage().first()
        val localMedia =
            mediaLocalDataSource.getHomeMediaByCategory(Category.UPCOMING, language)
        if (localMedia.isNotEmpty()) return localMedia.mapNotNull { it.toDomain() }

        return safeCall(FailedException("getUpComingMedia")) {
            val upcomingMovies =
                mediaRemoteDataSource.getUpcomingMovies(language = language).results?.mapNotNull {
                    it.toDomain(MediaType.Movie)
                } ?: emptyList()
            val entities = upcomingMovies.map {
                it.toMediaEntity(category = Category.UPCOMING, language)
            }
            mediaLocalDataSource.addHomeMedia(entities)
            upcomingMovies
        }
    }

    override suspend fun getNowPlayingMedia(): List<Media> {
        return safeCall(FailedException("getNowPlayingMedia")) {
            val nowPlaying = mediaRemoteDataSource.getNowPlayingMovies().results?.mapNotNull {
                it.toDomain(MediaType.Movie)
            } ?: emptyList()
            nowPlaying
        }
    }

    override suspend fun addMediaToContinueWatching(media: Media) {
        return safeCall(FailedException("addMediaToContinueWatching")) {
            mediaLocalDataSource.addMediaContinueWatching(media.toEntity())
        }
    }

    override  fun getContinueWatchingMedia(): Flow<List<Media>> {

        return mediaLocalDataSource.getMediaContinueWatching().map { mediaList ->

            val movies = mediaList.filter { it.type == MediaTypeEntity.Movie }
            val tvShows = mediaList.filter { it.type == MediaTypeEntity.TvShow }

            val filteredMovies = CoroutineScope(IO).async {
                movies.map { movieRepository.getMovieDetails(it.id) }
            }

            val filteredTvShows = CoroutineScope(IO).async {
                tvShows.map { tvShowRepository.getTvShowDetails(it.id) }
            }

            filteredMovies.await().map { it.toMedia() } + filteredTvShows.await().map { it.toMedia() }
        }

    }

    override suspend fun getRatedMedia(accountId: Int): List<Media> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getRatedMedia")) {
            val ratedMovies = mediaRemoteDataSource.getRatedMovies(accountId, language)
                .results.mapNotNull { it.toDomain(MediaType.Movie) }

            val ratedTvShows = mediaRemoteDataSource.getRatedTvShows(accountId, language)
                .results.mapNotNull {
                    it.toDomain(MediaType.TvShow)
                }
            ratedMovies + ratedTvShows
        }
    }

    override suspend fun getMoviesByCategory(
        category: com.paris_2.domain.media.entity.Category,
        page: Int
    ): List<Media> {
        return safeCall(FailedException("getMoviesByCategory")) {
            val language = settingLocalDataSource.getLanguage().first()
            mediaRemoteDataSource.getMoviesByCategory(
                category.toId(),
                page,
                language
            ).resultDto?.mapNotNull {
                it.toDomain()
            } ?: emptyList()
        }
    }

    override suspend fun getTvShowsByCategory(
        category: com.paris_2.domain.media.entity.Category,
        page: Int
    ): List<Media> {
        return safeCall(FailedException("getTvShowsByCategory")) {
            val language = settingLocalDataSource.getLanguage().first()
            mediaRemoteDataSource.getTvShowsByCategory(
                category.toId(),
                page,
                language
            ).tvResultDto.mapNotNull {
                it.toDomain()
            }
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