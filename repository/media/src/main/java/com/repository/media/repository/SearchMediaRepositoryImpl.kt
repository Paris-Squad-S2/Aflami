package com.repository.media.repository

import com.paris_2.domain.media.exception.NoMediaForActorException
import com.paris_2.domain.media.exception.NoMediaForCountryException
import com.paris_2.domain.media.exception.NoMediaForSearchException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.repository.SearchMediaRepository
import com.repository.media.datasource.local.HistoryLocalDataSource
import com.repository.media.datasource.local.MediaLocalDataSource
import com.repository.media.datasource.remote.SearchRemoteDataSource
import com.repository.media.entity.SearchType
import com.repository.media.mapper.search.toMediaEntities
import com.repository.media.mapper.search.toMediaEntitiesForActors
import com.repository.media.mapper.search.toMedias
import com.repository.media.util.NetworkConnectionChecker
import com.repository.media.util.detectLanguage
import com.repository.media.util.getCurrentDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlin.collections.isNotEmpty
import kotlin.time.ExperimentalTime

class SearchMediaRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val mediaLocalDataSource: MediaLocalDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val searchHistoryLocalDataSource: HistoryLocalDataSource
) : SearchMediaRepository {

    override suspend fun getMediaByActor(actorName: String,page:Int): List<Media> {
        val language = detectLanguage()
        return try {
            val localMedia = mediaLocalDataSource.getMediaByActor(actor = actorName,page=page , language = language)
            val queryDate = searchHistoryLocalDataSource
                .getSearchHistoryQuery(actorName, SearchType.Actor)
                ?.searchDate
            if (localMedia.isNotEmpty() && queryDate != null && isDataFresh(date = queryDate)) {
                return localMedia.toMedias()
            }

            if (networkConnectionChecker.isConnected.value) {
                val language = detectLanguage()
                val remoteDto =
                    searchRemoteDataSource.searchPerson(query = actorName, language = language, page = page)
                val entities = remoteDto.toMediaEntitiesForActors(query = actorName , language = language, page = page)
                searchHistoryLocalDataSource.addSearchQuery(
                    title = actorName,
                    searchType = SearchType.Actor
                )
                mediaLocalDataSource.addAllMedia(media = entities)
            } else {
                throw NoInternetConnectionException()
            }

            mediaLocalDataSource.getMediaByActor(actor = actorName, page = page,language=language).toMedias()
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            throw NoMediaForActorException()
        }
    }

    override suspend fun getMoviesByCountry(countryName: String,page: Int): List<Media> {
        val language = detectLanguage()
        return try {
            val localMedia = mediaLocalDataSource.getMediaByCountry(country = countryName,page = page, language = language)

            val queryDate = searchHistoryLocalDataSource
                .getSearchHistoryQuery(query = countryName, searchType = SearchType.Country)
                ?.searchDate
            if (localMedia.isNotEmpty() && queryDate != null && isDataFresh(date = queryDate)) {
                return localMedia.toMedias()
            }

            if (networkConnectionChecker.isConnected.value) {
                val language = detectLanguage()
                val remoteDto = searchRemoteDataSource.searchCountryCode(
                    countryCode = countryName,
                    language = language,
                    page = page,
                )
                val mediaEntities =
                    remoteDto.toMediaEntities(query = countryName, searchType = SearchType.Country,page=page,language=language)
                searchHistoryLocalDataSource.addSearchQuery(
                    title = countryName,
                    searchType = SearchType.Country
                )
                mediaLocalDataSource.addAllMedia(media = mediaEntities)
            } else {
                throw NoInternetConnectionException()
            }

            mediaLocalDataSource.getMediaByCountry(country = countryName,page,language=language).toMedias()
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            throw NoMediaForCountryException()
        }
    }

    override suspend fun getMediaByQuery(query: String,page: Int): List<Media> {
        val language = detectLanguage()
        return try {
            val localMedia = mediaLocalDataSource.getMediaByTitleQuery(query = query, page = page,language=language)

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
                    remoteDto.toMediaEntities(query = query, searchType = SearchType.Query,language=language , page = page)
                searchHistoryLocalDataSource.addSearchQuery(
                    title = query,
                    searchType = SearchType.Query
                )
                mediaLocalDataSource.addAllMedia(entities)
            } else {
                throw NoInternetConnectionException()
            }

            mediaLocalDataSource.getMediaByTitleQuery(query = query, page = page,language=language).toMedias()
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            throw NoMediaForSearchException()
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun isDataFresh(date: LocalDateTime): Boolean {
        val timeZone = TimeZone.currentSystemDefault()
        return date.toInstant(timeZone)
            .plus(1, DateTimeUnit.HOUR) >= getCurrentDate().toInstant(timeZone)
    }
}