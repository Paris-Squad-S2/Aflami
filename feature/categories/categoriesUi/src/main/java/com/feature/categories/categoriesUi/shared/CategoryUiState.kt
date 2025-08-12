package com.feature.categories.categoriesUi.shared

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.feature.categories.categoriesUi.R
import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.entity.MediaType
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
data class CategoryUiState(
    val category: Category,
    val type: MediaType,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
){
    companion object {
        fun getDefault() = CategoryUiState(
            category = Category.Action,
            type = MediaType.Movie,
            name = R.string.action,
            icon = R.drawable.img_category_action
        )
        fun getMoviesCategories(): List<CategoryUiState> = listOf(
            CategoryUiState(
                category = Category.Action,
                type = MediaType.Movie,
                name = R.string.action,
                icon = R.drawable.img_category_action
            ),
            CategoryUiState(
                category = Category.Western,
                type = MediaType.Movie,
                name = R.string.western,
                icon = R.drawable.img_category_western
            ),
            CategoryUiState(
                category = Category.Romance,
                type = MediaType.Movie,
                name = R.string.romance,
                icon = R.drawable.img_category_romance
            ),
            CategoryUiState(
                category = Category.Adventure,
                type = MediaType.Movie,
                name = R.string.adventure,
                icon = R.drawable.img_category_adventure
            ),
            CategoryUiState(
                category = Category.History,
                type = MediaType.Movie,
                name = R.string.history,
                icon = R.drawable.img_category_history
            ),
            CategoryUiState(
                category = Category.Family,
                type = MediaType.Movie,
                name = R.string.family,
                icon = R.drawable.img_category_family
            ),
            CategoryUiState(
                category = Category.TvMovie,
                type = MediaType.Movie,
                name = R.string.tv_movie,
                icon = R.drawable.img_category_tv_movie
            ),
            CategoryUiState(
                category = Category.Horror,
                type = MediaType.Movie,
                name = R.string.horror,
                icon = R.drawable.img_category_horror
            ),
            CategoryUiState(
                category = Category.War,
                type = MediaType.Movie,
                name = R.string.war,
                icon = R.drawable.img_category_war
            ),
            CategoryUiState(
                category = Category.Comedy,
                type = MediaType.Movie,
                name = R.string.comedy,
                icon = R.drawable.img_category_comedy
            ),
            CategoryUiState(
                category = Category.Mystery,
                type = MediaType.Movie,
                name = R.string.mystery,
                icon = R.drawable.img_category_mystery
            ),
            CategoryUiState(
                category = Category.Music,
                type = MediaType.Movie,
                name = R.string.music,
                icon = R.drawable.img_category_music
            ),
            CategoryUiState(
                category = Category.Thriller,
                type = MediaType.Movie,
                name = R.string.thriller,
                icon = R.drawable.img_category_thriller
            ),
            CategoryUiState(
                category = Category.ScienceFiction,
                type = MediaType.Movie,
                name = R.string.science_fiction,
                icon = R.drawable.img_category_science_fiction
            ),
            CategoryUiState(
                category = Category.Documentary,
                type = MediaType.Movie,
                name = R.string.documentary,
                icon = R.drawable.img_category_documentary
            ),
            CategoryUiState(
                category = Category.Fantasy,
                type = MediaType.Movie,
                name = R.string.fantasy,
                icon = R.drawable.img_category_fantasy
            ),
            CategoryUiState(
                category = Category.Drama,
                type = MediaType.Movie,
                name = R.string.drama,
                icon = R.drawable.img_category_drama
            ),
            CategoryUiState(
                category = Category.Crime,
                type = MediaType.Movie,
                name = R.string.crime,
                icon = R.drawable.img_category_crime
            ),
            CategoryUiState(
                category = Category.Animation,
                type = MediaType.Movie,
                name = R.string.animation,
                icon = R.drawable.img_category_animation
            ),
        )

        fun getTvShowsCategories(): List<CategoryUiState> = listOf(
            CategoryUiState(
                category = Category.ActionAdventure,
                type = MediaType.TvShow,
                name = R.string.action_adventure,
                icon = R.drawable.img_category_adventure
            ),
            CategoryUiState(
                category = Category.Western,
                type = MediaType.TvShow,
                name = R.string.western,
                icon = R.drawable.img_category_western
            ),
            CategoryUiState(
                category = Category.Family,
                type = MediaType.TvShow,
                name = R.string.family,
                icon = R.drawable.img_category_family
            ),
            CategoryUiState(
                category = Category.Kids,
                type = MediaType.TvShow,
                name = R.string.kids,
                icon = R.drawable.img_category_kids
            ),
            CategoryUiState(
                category = Category.Comedy,
                type = MediaType.TvShow,
                name = R.string.comedy,
                icon = R.drawable.img_category_comedy
            ),
            CategoryUiState(
                category = Category.War,
                type = MediaType.TvShow,
                name = R.string.war,
                icon = R.drawable.img_category_war
            ),
            CategoryUiState(
                category = Category.Mystery,
                type = MediaType.TvShow,
                name = R.string.mystery,
                icon = R.drawable.img_category_mystery
            ),
            CategoryUiState(
                category = Category.ScienceFiction,
                type = MediaType.TvShow,
                name = R.string.science_fiction,
                icon = R.drawable.img_category_science_fiction
            ),
            CategoryUiState(
                category = Category.Documentary,
                type = MediaType.TvShow,
                name = R.string.documentary,
                icon = R.drawable.img_category_documentary
            ),
            CategoryUiState(
                category = Category.Drama,
                type = MediaType.TvShow,
                name = R.string.drama,
                icon = R.drawable.img_category_drama
            ),
            CategoryUiState(
                category = Category.Crime,
                type = MediaType.TvShow,
                name = R.string.crime,
                icon = R.drawable.img_category_crime
            ),
            CategoryUiState(
                category = Category.News,
                type = MediaType.TvShow,
                name = R.string.news,
                icon = R.drawable.img_category_news
            ),
            CategoryUiState(
                category = Category.Talk,
                type = MediaType.TvShow,
                name = R.string.talk,
                icon = R.drawable.img_category_talk
            ),
            CategoryUiState(
                category = Category.Reality,
                type = MediaType.TvShow,
                name = R.string.reality,
                icon = R.drawable.img_category_reality
            ),
            CategoryUiState(
                category = Category.Soap,
                type = MediaType.TvShow,
                name = R.string.soap,
                icon = R.drawable.img_category_soap
            ),
        )
    }
}

@OptIn(InternalSerializationApi::class)
fun CategoryUiState.toJson(): String =
    Json.encodeToString(CategoryUiState::class.serializer(), this)

fun String.fromJsonToCategoryUiState(): CategoryUiState =
    Json.decodeFromString(this)