package com.feature.categories.categoriesUi.screen.categories

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.feature.categories.categoriesUi.R
import com.paris_2.domain.media.entity.Category

data class CategoriesScreenUIState(
    val categoriesUIState: CategoriesUIState = CategoriesUIState(),
    val status: Status = Status.Normal
)

enum class Status {
    Normal,
    Loading,
    NetworkError,
    UnknownError,
}

data class CategoriesUIState(
    val selectedTabIndex: Int = 0,
    val moviesCategories: List<CategoryUiState> = listOf(
        CategoryUiState(
            category = Category.Action,
            name = R.string.action,
            icon = R.drawable.img_category_action
        ),
        CategoryUiState(
            category = Category.Western,
            name = R.string.western,
            icon = R.drawable.img_category_western
        ),
        CategoryUiState(
            category = Category.Romance,
            name = R.string.romance,
            icon = R.drawable.img_category_romance
        ),
        CategoryUiState(
            category = Category.Adventure,
            name = R.string.adventure,
            icon = R.drawable.img_category_adventure
        ),
        CategoryUiState(
            category = Category.History,
            name = R.string.history,
            icon = R.drawable.img_category_history
        ),
        CategoryUiState(
            category = Category.Family,
            name = R.string.family,
            icon = R.drawable.img_category_family
        ),
        CategoryUiState(
            category = Category.TvMovie,
            name = R.string.tv_movie,
            icon = R.drawable.img_category_tv_movie
        ),
        CategoryUiState(
            category = Category.Horror,
            name = R.string.horror,
            icon = R.drawable.img_category_horror
        ),
        CategoryUiState(
            category = Category.War,
            name = R.string.war,
            icon = R.drawable.img_category_war
        ),
        CategoryUiState(
            category = Category.Comedy,
            name = R.string.comedy,
            icon = R.drawable.img_category_comedy
        ),
        CategoryUiState(
            category = Category.Mystery,
            name = R.string.mystery,
            icon = R.drawable.img_category_mystery
        ),
        CategoryUiState(
            category = Category.Music,
            name = R.string.music,
            icon = R.drawable.img_category_music
        ),
        CategoryUiState(
            category = Category.Thriller,
            name = R.string.thriller,
            icon = R.drawable.img_category_thriller
        ),
        CategoryUiState(
            category = Category.ScienceFiction,
            name = R.string.science_fiction,
            icon = R.drawable.img_category_science_fiction
        ),
        CategoryUiState(
            category = Category.Documentary,
            name = R.string.documentary,
            icon = R.drawable.img_category_documentary
        ),
        CategoryUiState(
            category = Category.Fantasy,
            name = R.string.fantasy,
            icon = R.drawable.img_category_fantasy
        ),
        CategoryUiState(
            category = Category.Drama,
            name = R.string.drama,
            icon = R.drawable.img_category_drama
        ),
        CategoryUiState(
            category = Category.Crime,
            name = R.string.crime,
            icon = R.drawable.img_category_crime
        ),
        CategoryUiState(
            category = Category.Animation,
            name = R.string.animation,
            icon = R.drawable.img_category_animation
        ),
    ),
    val tvShowsCategories: List<CategoryUiState> = listOf(
        CategoryUiState(
            category = Category.ActionAdventure,
            name = R.string.action_adventure,
            icon = R.drawable.img_category_adventure
        ),
        CategoryUiState(
            category = Category.Western,
            name = R.string.western,
            icon = R.drawable.img_category_western
        ),
        CategoryUiState(
            category = Category.Family,
            name = R.string.family,
            icon = R.drawable.img_category_family
        ),
        CategoryUiState(
            category = Category.Kids,
            name = R.string.kids,
            icon = R.drawable.img_category_kids
        ),
        CategoryUiState(
            category = Category.Comedy,
            name = R.string.comedy,
            icon = R.drawable.img_category_comedy
        ),
        CategoryUiState(
            category = Category.War,
            name = R.string.war,
            icon = R.drawable.img_category_war
        ),
        CategoryUiState(
            category = Category.Mystery,
            name = R.string.mystery,
            icon = R.drawable.img_category_mystery
        ),
        CategoryUiState(
            category = Category.ScienceFiction,
            name = R.string.science_fiction,
            icon = R.drawable.img_category_science_fiction
        ),
        CategoryUiState(
            category = Category.Documentary,
            name = R.string.documentary,
            icon = R.drawable.img_category_documentary
        ),
        CategoryUiState(
            category = Category.Drama,
            name = R.string.drama,
            icon = R.drawable.img_category_drama
        ),
        CategoryUiState(
            category = Category.Crime,
            name = R.string.crime,
            icon = R.drawable.img_category_crime
        ),
        CategoryUiState(
            category = Category.News,
            name = R.string.news,
            icon = R.drawable.img_category_news
        ),
        CategoryUiState(
            category = Category.Talk,
            name = R.string.talk,
            icon = R.drawable.img_category_talk
        ),
        CategoryUiState(
            category = Category.Reality,
            name = R.string.reality,
            icon = R.drawable.img_category_reality
        ),
        CategoryUiState(
            category = Category.Soap,
            name = R.string.soap,
            icon = R.drawable.img_category_soap
        ),
    )
)

data class CategoryUiState(
    val category: Category,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
)