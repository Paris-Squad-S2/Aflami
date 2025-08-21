package com.repository.media.repository

import com.paris.domain.media.entity.Media
import com.paris.domain.media.exception.FailedException
import com.paris.domain.media.repository.SearchMediaRepository
import com.paris.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.media.datasource.local.HistoryLocalDataSource
import com.repository.media.datasource.remote.SearchRemoteDataSource
import com.repository.media.mapper.search.toMedia
import com.repository.media.models.local.media.SearchType
import com.repository.media.util.NetworkConnectionChecker
import com.repository.media.util.safeCall
import kotlinx.coroutines.flow.first

class SearchMediaRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val searchHistoryLocalDataSource: HistoryLocalDataSource,
    private val settingLocalDataSource: SettingLocalDataSource,
) : SearchMediaRepository {

    override suspend fun getMediaByActor(actorName: String, page: Int): List<Media> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getMediaByActor"), networkConnectionChecker) {
            val remoteDto = searchRemoteDataSource.searchPerson(
                query = actorName,
                language = language,
                page = page
            )
            searchHistoryLocalDataSource.addSearchQuery(
                title = actorName,
                searchType = SearchType.Actor
            )
            remoteDto.results
                ?.flatMap { resultDto ->
                    resultDto.knownForDTO?.mapNotNull { it.toMedia() } ?: emptyList()
                } ?: emptyList()
        }
    }

    override suspend fun getMoviesByCountry(countryName: String, page: Int): List<Media> {
        val language =  settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getMoviesByCountry"), networkConnectionChecker) {
            val remoteDto = searchRemoteDataSource.searchCountryCode(
                countryCode = countryName,
                language = language,
                page = page
            )
            searchHistoryLocalDataSource.addSearchQuery(
                title = countryName,
                searchType = SearchType.Country
            )
            remoteDto.results?.mapNotNull {
                it.toMedia()
            } ?: emptyList()
        }
    }

    override suspend fun getMediaByQuery(query: String, page: Int): List<Media> {
        val language =  settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getMediaByQuery"), networkConnectionChecker) {
            val remoteDto = searchRemoteDataSource.searchMulti(
                query = query,
                language = language,
                page = page
            )
            searchHistoryLocalDataSource.addSearchQuery(
                title = query,
                searchType = SearchType.Query
            )
            remoteDto.results?.mapNotNull { it.toMedia() } ?: emptyList()
        }
    }

}
