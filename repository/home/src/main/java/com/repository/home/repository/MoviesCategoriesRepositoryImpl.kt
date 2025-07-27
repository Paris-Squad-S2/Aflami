package com.repository.home.repository

import com.domain.home.exception.AflamiException
import com.domain.home.exception.NoCategoriesFoundException
import com.domain.home.exception.NoInternetConnectionException
import com.domain.home.model.Category
import com.domain.home.repository.MoviesCategoriesRepository
import com.repository.home.datasource.remote.GenresRemoteDataSource
import com.repository.home.mapper.toCategoryList
import com.repository.home.util.HomeNetworkConnectionChecker
import com.repository.home.util.detectLanguage

class MoviesCategoriesRepositoryImpl(
    private val genresRemoteDataSource: GenresRemoteDataSource,
    private val networkConnectionChecker: HomeNetworkConnectionChecker,
) : MoviesCategoriesRepository {
    override suspend fun getMoviesCategories(): List<Category> {
        val language = detectLanguage()
        return safeCall(NoCategoriesFoundException()) {
            val genresDto = genresRemoteDataSource.getMoviesGenres(language)
            val categories = genresDto.toCategoryList()
            if (categories.isEmpty()) {
                throw NoCategoriesFoundException()
            }
            categories
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