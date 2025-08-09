package com.repository.media.repository

import com.paris_2.domain.media.entity.Genre
import com.paris_2.domain.media.exception.AflamiException
import com.paris_2.domain.media.exception.NoCategoriesFoundException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.repository.CategoriesRepository
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.mapper.toGenreList
import com.repository.media.util.NetworkConnectionChecker
import kotlinx.coroutines.flow.first

class CategoriesRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val genresRemoteDataSource: GenresRemoteDataSource,
    private val settingLocalDataSource: SettingLocalDataSource
) : CategoriesRepository {
    override suspend fun getAllCategories(): List<Genre> {
        val language = languageLocalDataSourceRepository.getLanguage().first()

        return safeCall(NoCategoriesFoundException()) {
            val remoteGenres = genresRemoteDataSource.getMoviesGenres(language).genreDto
            remoteGenres?.toGenreList() ?: emptyList()
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
