package com.repository.media.repository

import com.paris.domain.media.entity.Category
import com.paris.domain.media.exception.FailedException
import com.paris.domain.media.repository.MoviesCategoriesRepository
import com.paris.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.mapper.genreListToCategoryList
import com.repository.media.util.NetworkConnectionChecker
import com.repository.media.util.safeCall
import kotlinx.coroutines.flow.first

class MoviesCategoriesRepositoryImpl(
    private val genresRemoteDataSource: GenresRemoteDataSource,
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val settingLocalDataSource: SettingLocalDataSource
) : MoviesCategoriesRepository {
    override suspend fun getMoviesCategories(): List<Category> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getMoviesCategories"), networkConnectionChecker) {
            val genresDto = genresRemoteDataSource.getMoviesGenres(language)
            genresDto.genreListToCategoryList()
        }
    }

}