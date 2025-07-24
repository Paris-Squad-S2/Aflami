package com.repository.home.repository


import com.domain.home.exception.NoPopularMediaFoundException
import com.domain.home.exception.NoTopRatingMediaFoundException
import com.domain.home.exception.NoUpComingMediaFoundException
import com.domain.home.exception.NoNowPlayingMediaFoundException
import com.domain.home.model.Media
import com.domain.home.model.MediaType
import com.domain.home.repository.MediaRepository
import com.repository.home.datasource.local.HomeMediaLocalDataSource
import com.repository.home.datasource.remote.MediaRemoteDataSource
import com.repository.home.mapper.toDomain
import com.repository.home.mapper.toEntity
import java.util.Locale

class MediaRepositoryImpl(
    private val mediaRemoteDataSource: MediaRemoteDataSource,
    private val homeMediaLocalDataSource: HomeMediaLocalDataSource
) : MediaRepository {

    override suspend fun getPopularMedia(): List<Media> {
        val language = Locale.getDefault().language
        return try {
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
            if(combined.isEmpty()) throw NoPopularMediaFoundException()
            combined
        } catch (e: Exception) {
            throw NoPopularMediaFoundException()
        }
    }

    override suspend fun getTopRatingMedia(): List<Media> {
        val language = Locale.getDefault().language
        return try {
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
            if (combined.isEmpty()) throw NoTopRatingMediaFoundException()
            combined
        } catch (e: Exception) {
            throw NoTopRatingMediaFoundException()
        }
    }

    override suspend fun getUpComingMedia(): List<Media> {
        val language = Locale.getDefault().language
        return try {
            val upcomingMovies = mediaRemoteDataSource.getUpcomingMovies(language = language).results?.mapNotNull {
                it.toDomain(MediaType.MOVIE)
            } ?: emptyList()
            if (upcomingMovies.isEmpty()) throw NoUpComingMediaFoundException()
            upcomingMovies
        } catch (e: Exception) {
            throw NoUpComingMediaFoundException()
        }
    }

    override suspend fun getNowPlayingMedia(): List<Media> {
        return try {
            val nowPlaying = mediaRemoteDataSource.getNowPlayingMovies().results?.mapNotNull {
                it.toDomain(MediaType.MOVIE)
            } ?: emptyList()
            if (nowPlaying.isEmpty()) throw NoNowPlayingMediaFoundException()
            nowPlaying
        } catch (e: Exception) {
            throw NoNowPlayingMediaFoundException()
        }
    }

    override suspend fun addMediaToLocal(media: Media) {
        homeMediaLocalDataSource.addMedia(media.toEntity())
    }

    override suspend fun getMediaFromLocal(): List<Media> {
        return homeMediaLocalDataSource.getAllMedia().map { it.toDomain() }
    }
}
