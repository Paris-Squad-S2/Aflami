package com.repository.media.repository

import com.paris.domain.media.entity.Category
import com.paris.domain.media.exception.FailedException
import com.paris.domain.media.repository.CategoriesRepository
import com.paris.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.mapper.search.toCategories
import com.repository.media.util.NetworkConnectionChecker
import com.repository.media.util.safeCall
import kotlinx.coroutines.flow.first

class CategoriesRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val genresRemoteDataSource: GenresRemoteDataSource,
    private val settingLocalDataSource: SettingLocalDataSource
) : CategoriesRepository {
    override suspend fun getAllCategories(): List<Category> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getAllCategories"), networkConnectionChecker) {
            val remoteGenres = genresRemoteDataSource.getMoviesGenres(language).genreDto
            remoteGenres?.toCategories() ?: emptyList()
        }
    }
}
