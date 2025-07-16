package com.repository.search.repository

import com.domain.search.exception.NoDataForActorException
import com.domain.search.exception.NoDataForCountryException
import com.domain.search.exception.NoDataForSearchException
import com.domain.search.exception.NoInternetConnectionException
import com.domain.search.model.Media
import com.domain.search.repository.SearchMediaRepository
import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.dataSource.local.MediaLocalDataSource
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import com.repository.search.entity.SearchType
import com.repository.search.mapper.toMediaEntities
import com.repository.search.mapper.toMediaEntitiesForActors
import com.repository.search.mapper.toMedias
import com.repository.search.util.detectLanguage
import com.repository.search.util.getCurrentDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
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

    override suspend fun getMediaByActor(actorName: String,page:Int): List<Media> {
        return try {
            val localMedia = mediaLocalDataSource.getMediaByActor(actor = actorName,page)
            val queryDate = searchHistoryLocalDataSource
                .getSearchHistoryQuery(actorName, SearchType.Actor)
                ?.searchDate
            if (localMedia.isNotEmpty() && queryDate != null && isDataFresh(date = queryDate)) {
                return localMedia.toMedias()
            }

            if (networkConnectionChecker.isConnected.value) {
                val language = detectLanguage()
                val remoteDto =
                    searchRemoteDataSource.searchPerson(query = actorName, language = language , page = page)
                val entities = remoteDto.toMediaEntitiesForActors(query = actorName,page = page)
                searchHistoryLocalDataSource.addSearchQuery(
                    title = actorName,
                    searchType = SearchType.Actor
                )
                mediaLocalDataSource.addAllMedia(media = entities)
            } else {
                throw NoInternetConnectionException()
            }

            mediaLocalDataSource.getMediaByActor(actor = actorName, page = page).toMedias()
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            throw NoDataForActorException()
        }
    }

    override suspend fun getMoviesByCountry(countryName: String,page: Int): List<Media> {
        return try {
            val localMedia = mediaLocalDataSource.getMediaByCountry(country = countryName,page = page)

            val queryDate = searchHistoryLocalDataSource
                .getSearchHistoryQuery(query = countryName, searchType = SearchType.Country)
                ?.searchDate
            if (localMedia.isNotEmpty() && queryDate != null && isDataFresh(date = queryDate)) {
                return localMedia.toMedias()
            }

            if (networkConnectionChecker.isConnected.value) {
                val language = detectLanguage()
                val remoteDto = searchRemoteDataSource.searchCountryCode(
                    query = countryName,
                    countryCode = countryName,
                    language = language,
                    page = page,
                )
                val mediaEntities =
                    remoteDto.toMediaEntities(query = countryName, searchType = SearchType.Country,page=page)
                searchHistoryLocalDataSource.addSearchQuery(
                    title = countryName,
                    searchType = SearchType.Country
                )
                mediaLocalDataSource.addAllMedia(media = mediaEntities)
            } else {
                throw NoInternetConnectionException()
            }

            mediaLocalDataSource.getMediaByCountry(country = countryName,page).toMedias()
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            throw NoDataForCountryException()
        }
    }

    override suspend fun getMediaByQuery(query: String,page: Int): List<Media> {
        return try {
            val localMedia = mediaLocalDataSource.getMediaByTitleQuery(query = query, page = page)

            val queryDate = searchHistoryLocalDataSource
                .getSearchHistoryQuery(query, SearchType.Query)
                ?.searchDate
            if (localMedia.isNotEmpty() && queryDate != null && isDataFresh(date = queryDate)) {
                return localMedia.toMedias()
            }

            if (networkConnectionChecker.isConnected.value) {
                val language = detectLanguage()
                val remoteDto =
                    searchRemoteDataSource.searchMulti(query = query, language = language, page = page)
                val entities =
                    remoteDto.toMediaEntities(query = query, searchType = SearchType.Query, page = page)
                searchHistoryLocalDataSource.addSearchQuery(
                    title = query,
                    searchType = SearchType.Query
                )
                mediaLocalDataSource.addAllMedia(entities)
            } else {
                throw NoInternetConnectionException()
            }

            mediaLocalDataSource.getMediaByTitleQuery(query = query, page = page).toMedias()
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            throw NoDataForSearchException()
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun isDataFresh(date: LocalDateTime): Boolean {
        val timeZone = TimeZone.currentSystemDefault()
        return date.toInstant(timeZone)
            .plus(1, DateTimeUnit.HOUR) >= getCurrentDate().toInstant(timeZone)
    }
}