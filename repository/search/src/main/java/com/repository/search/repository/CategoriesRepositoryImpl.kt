package com.repository.search.repository

import com.paris_2.domain.media.exception.NoCategoriesFoundException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.exception.AflamiException
import com.paris_2.domain.media.repository.CategoriesRepository
import com.repository.search.util.NetworkConnectionChecker
import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.repository.search.mapper.toCategories
import java.util.Locale

class CategoriesRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val genresRemoteDataSource: GenresRemoteDataSource,
) : CategoriesRepository {

    override suspend fun getAllCategories(): List<Category> {
        val language = Locale.getDefault().language
        return safeCall(NoCategoriesFoundException()) {
            val remoteGenres = genresRemoteDataSource.getAllGenres(language).genreDto
            remoteGenres?.toCategories() ?: emptyList()
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
