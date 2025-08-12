package com.repository.media.repository

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.exception.AflamiException
import com.paris_2.domain.media.exception.FailedException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.repository.MoviesCategoriesRepository
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.mapper.genreListToCategoryList
import com.repository.media.util.NetworkConnectionChecker
import kotlinx.coroutines.flow.first

class MoviesCategoriesRepositoryImpl(
    private val genresRemoteDataSource: GenresRemoteDataSource,
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val settingLocalDataSource: SettingLocalDataSource
): MoviesCategoriesRepository {
    override suspend fun getMoviesCategories(): List<Category> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getMoviesCategories")) {
            val genresDto = genresRemoteDataSource.getMoviesGenres(language)
            genresDto.genreListToCategoryList()
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