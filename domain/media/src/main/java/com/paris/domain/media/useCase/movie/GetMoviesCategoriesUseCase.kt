package com.paris.domain.media.useCase.movie

import com.paris.domain.media.entity.Category

class GetMoviesCategoriesUseCase() {
    operator fun invoke(): List<Category> {
        return listOf(
            Category.Action,
            Category.Adventure,
            Category.Animation,
            Category.Comedy,
            Category.Crime,
            Category.Documentary,
            Category.Drama,
            Category.Family,
            Category.Fantasy,
            Category.History,
            Category.Horror,
            Category.Music,
            Category.Mystery,
            Category.Romance,
            Category.ScienceFiction,
            Category.TvMovie,
            Category.Thriller,
            Category.War,
            Category.Western
        )
    }
}