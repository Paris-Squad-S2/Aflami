package com.repository.search.repository

import android.util.Log
import com.domain.search.model.Media
import com.domain.search.repository.SearchMediaRepository
import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.dataSource.local.MediaLocalDataSource
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import com.repository.search.entity.SearchType
import com.repository.search.exception.NoDataForActorException
import com.repository.search.exception.NoDataForCountryException
import com.repository.search.exception.NoDataForSearchException
import com.repository.search.exception.NoInternetConnectionException
import com.repository.search.mapper.toMediaEntities
import com.repository.search.mapper.toMediaEntitiesForActors
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
                val queryDate = searchHistoryLocalDataSource.getSearchHistoryQuery(actorName, SearchType.Actor)?.searchDate
                val timeZone = TimeZone.Companion.currentSystemDefault()
                if (queryDate != null && queryDate.toInstant(timeZone)
                        .plus(1, DateTimeUnit.HOUR) >= getCurrentDate().toInstant(timeZone)
                ) {
                    return media.toMedias()
                }
            }
            if (networkConnectionChecker.isConnected.value) {
                val searchDto = searchRemoteDataSource.searchPerson(query = actorName)
                val mediaEntities = searchDto.toMediaEntitiesForActors(
                    query = actorName
                )
                searchHistoryLocalDataSource.addSearchQuery(actorName, SearchType.Actor)
                mediaLocalDataSource.addAllMedia(mediaEntities)
            } else {
                throw NoInternetConnectionException()
            }
            return mediaLocalDataSource.getMediaByActor(actor = actorName).toMedias()

        } catch (e: NoInternetConnectionException) {
            throw e
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
                    searchHistoryLocalDataSource.getSearchHistoryQuery(countryName, SearchType.Country)?.searchDate
                val timeZone = TimeZone.Companion.currentSystemDefault()
                if (queryDate != null && queryDate.toInstant(timeZone)
                        .plus(1, DateTimeUnit.HOUR) >= getCurrentDate().toInstant(timeZone)
                ) {
                    return media.toMedias()
                }
            }
            if (networkConnectionChecker.isConnected.value) {
                val searchDto = searchRemoteDataSource.searchCountryCode(
                    query = countryName,
                    countryCode = countryName
                )
                val mediaEntities = searchDto.toMediaEntities(
                    query = countryName,
                    searchType = SearchType.Country
                )
                searchHistoryLocalDataSource.addSearchQuery(countryName, SearchType.Country)
                mediaLocalDataSource.addAllMedia(mediaEntities)
            } else {
                throw NoInternetConnectionException()
            }
            return mediaLocalDataSource.getMediaByCountry(country = countryName).toMedias()
        } catch (e: NoInternetConnectionException) {
            throw e
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
                    searchHistoryLocalDataSource.getSearchHistoryQuery(query, SearchType.Query)?.searchDate
                val timeZone = TimeZone.Companion.currentSystemDefault()
                if (queryDate != null && queryDate.toInstant(timeZone)
                        .plus(1, DateTimeUnit.HOUR) >= getCurrentDate().toInstant(timeZone)
                ) {
                    return media.toMedias()
                }
            }
            if (networkConnectionChecker.isConnected.value) {
                val searchDto = searchRemoteDataSource.searchMulti(query)
                val mediaEntities = searchDto.toMediaEntities(
                    query = query,
                    searchType = SearchType.Query
                )
                Log.d("SearchMediaRepositoryImpl", "getMediaByActor: $mediaEntities")

                searchHistoryLocalDataSource.addSearchQuery(query, SearchType.Query)

                mediaLocalDataSource.addAllMedia(mediaEntities)
            } else {
                throw NoInternetConnectionException()
            }
            return mediaLocalDataSource.getMediaByTitleQuery(query = query).toMedias()
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            throw NoDataForSearchException()
        }
    }
}