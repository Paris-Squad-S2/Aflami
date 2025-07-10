package com.repository.search.repository

import com.domain.search.model.Media
import com.domain.search.repository.SearchMediaRepository
import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.dataSource.local.MediaLocalDataSource
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import com.repository.search.exception.NoDataForActorException
import com.repository.search.exception.NoDataForCountryException
import com.repository.search.exception.NoDataForSearchException
import com.repository.search.exception.NoInternetConnectionException
import com.repository.search.mapper.toMediaEntities
import com.repository.search.mapper.toMedias
import com.repository.search.util.getCurrentDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

class SearchMediaRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val mediaLocalDataSource: MediaLocalDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val searchHistoryLocalDataSource: HistoryLocalDataSource
) : SearchMediaRepository {

    @OptIn(ExperimentalTime::class)
    override suspend fun getMediaByActor(actorName: String): List<Media> {
        try {
            val media = mediaLocalDataSource.getMediaByActor(actor = actorName)
            if (media.isNotEmpty()) {
                val queryDate =
                    searchHistoryLocalDataSource.getSearchHistoryQuery(actorName)?.searchDate
                val timeZone = TimeZone.Companion.currentSystemDefault()
                if (queryDate != null && queryDate.toInstant(timeZone)
                        .plus(1, DateTimeUnit.HOUR) <= getCurrentDate().toInstant(timeZone)
                ) {
                    return media.toMedias()
                } else {
                    mediaLocalDataSource.clearAllMediaBySearchQuery(actorName)
                }
            }
            if (networkConnectionChecker.isConnected.value) {
                val searchDto = searchRemoteDataSource.searchPerson(query = actorName)
                val mediaEntities = searchDto.toMediaEntities(
                    query = actorName,
                    actor = listOf(actorName)
                )
                searchHistoryLocalDataSource.addSearchQuery(actorName)
                mediaLocalDataSource.addAllMedia(mediaEntities)
            } else {
                throw NoInternetConnectionException()
            }
            return mediaLocalDataSource.getMediaByActor(actor = actorName).toMedias()

        } catch (_: Exception) {
            throw NoDataForActorException()
        }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun getMoviesByCountry(countryName: String): List<Media> {
        try {
            val media = mediaLocalDataSource.getMediaByCountry(country = countryName)
            if (media.isNotEmpty()) {
                val queryDate =
                    searchHistoryLocalDataSource.getSearchHistoryQuery(countryName)?.searchDate
                val timeZone = TimeZone.Companion.currentSystemDefault()
                if (queryDate != null && queryDate.toInstant(timeZone)
                        .plus(1, DateTimeUnit.HOUR) <= getCurrentDate().toInstant(timeZone)
                ) {
                    return media.toMedias()
                } else {
                    mediaLocalDataSource.clearAllMediaBySearchQuery(countryName)
                }
            }
            if (networkConnectionChecker.isConnected.value) {
                val searchDto = searchRemoteDataSource.searchCountryCode(query = countryName)
                val mediaEntities = searchDto.toMediaEntities(
                    query = countryName,
                    country = countryName
                )
                searchHistoryLocalDataSource.addSearchQuery(countryName)
                mediaLocalDataSource.addAllMedia(mediaEntities)
            } else {
                throw NoInternetConnectionException()
            }
            return mediaLocalDataSource.getMediaByCountry(country = countryName).toMedias()
        } catch (_: Exception) {
            throw NoDataForCountryException()
        }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun getMediaByQuery(query: String): List<Media> {
        try {
            val media = mediaLocalDataSource.getMediaByTitleQuery(query = query)
            if (media.isNotEmpty()) {
                val queryDate =
                    searchHistoryLocalDataSource.getSearchHistoryQuery(query)?.searchDate
                val timeZone = TimeZone.Companion.currentSystemDefault()
                if (queryDate != null && queryDate.toInstant(timeZone)
                        .plus(1, DateTimeUnit.HOUR) <= getCurrentDate().toInstant(timeZone)
                ) {
                    return media.toMedias()
                } else {
                    mediaLocalDataSource.clearAllMediaBySearchQuery(query)
                }
            }
            if (networkConnectionChecker.isConnected.value) {
                val searchDto = searchRemoteDataSource.searchMulti(query)
                val mediaEntities = searchDto.toMediaEntities(
                    query = query
                )
                searchHistoryLocalDataSource.addSearchQuery(query)
                mediaLocalDataSource.addAllMedia(mediaEntities)
            } else
                throw NoInternetConnectionException()
            return mediaLocalDataSource.getMediaByTitleQuery(query = query).toMedias()
        } catch (_: Exception) {
            throw NoDataForSearchException()
        }
    }
}