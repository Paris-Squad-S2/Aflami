package com.repository.search.repository

import com.domain.search.model.CategoryModel
import com.domain.search.repository.CategoriesRepository
import com.repository.search.dataSource.GenresLocalDataSource
import com.repository.search.entity.GenreEntity

class CategoriesRepositoryImpl(
    private val isNetworkConnected: Boolean, // impl the way to check the network connection
    private val genresLocalDataSource: GenresLocalDataSource,
//    private val genresRemoteDataSource: GenresRemoteDataSource,
) : CategoriesRepository {
    override suspend fun getAllCategories(): List<CategoryModel> {
        return if (isNetworkConnected)
            TODO()
        else
            genresLocalDataSource.getGenres().toCategories()

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