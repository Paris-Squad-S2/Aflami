package com.paris.domain.media.useCase.search

import com.paris.domain.media.entity.Category

class GetAllCategoriesUseCase {
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
            Category.Western,
            Category.ActionAdventure,
            Category.Kids,
            Category.News,
            Category.Reality,
            Category.ScifiFantasy,
            Category.Soap,
            Category.Talk,
            Category.WarPolitics
        )
    }
}