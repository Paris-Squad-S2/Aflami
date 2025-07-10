package com.repository.search.repository

import com.domain.search.model.CategoryModel
import com.domain.search.repository.CategoriesRepository
import com.example.search.GenresRemoteDataSource
import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.GenresLocalDataSource
import com.repository.search.entity.GenreEntity
import com.repository.search.mapper.toCategoryModels

class CategoriesRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val genresLocalDataSource: GenresLocalDataSource,
    private val genresRemoteDataSource: GenresRemoteDataSource,
) : CategoriesRepository {

    override suspend fun getAllCategories(): List<CategoryModel> {

        return try {
            if (networkConnectionChecker.isConnected.value)
                genresRemoteDataSource.getAllGenres().genres.toCategoryModels()
            else
                genresLocalDataSource.getGenres().toCategories()
        } catch (e: Exception) {
            throw e
        }

    }

}


fun List<GenreEntity>.toCategories(): List<CategoryModel> {
    return this.map { it.toCategoryModel() }
}


fun GenreEntity.toCategoryModel(): CategoryModel {
    return CategoryModel(
        id = this.id.toInt(), // need to change
        name = this.name
    )
}