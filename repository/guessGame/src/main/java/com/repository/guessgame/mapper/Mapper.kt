package com.repository.guessgame.mapper

import com.paris_2.domain.game.entity.Actor
import com.paris_2.domain.game.entity.ActorMedia
import com.paris_2.domain.game.entity.UserPoints
import com.paris_2.domain.media.entity.Category
import com.repository.guessgame.dto.ActorDto
import com.repository.guessgame.dto.ActorMediaDto
import com.repository.guessgame.entity.UserGamePointsEntity
import com.repository.guessgame.utils.isEnglish
import kotlinx.datetime.LocalDate

fun ActorDto.toDomain(): Actor? {
    if (profilePath.isNullOrEmpty()) return null
    val actorMedia = knownFor?.mapNotNull { it?.toDomain() } ?: emptyList()
    if (actorMedia.isEmpty()) return null
    val safeName = name?.takeIf { it.isEnglish() } ?: return null

    return Actor(
        id = id ?: -1,
        name = safeName,
        imageUri = profilePath,
        media = actorMedia
    )
}

fun ActorMediaDto.toDomain(): ActorMedia? {
    if (posterPath.isNullOrEmpty()) return null
    val safeTitle = title?.takeIf { it.isEnglish() } ?: return null

    val dateString = releaseDate ?: firstAirDate
    val parsedDate = dateString?.let {
        runCatching { LocalDate.parse(it) }.getOrNull()
    } ?: return null

    return ActorMedia(
        id = id ?: -1,
        name = safeTitle,
        posterImg = posterPath ,
        yearOfRelease = parsedDate,
        genres = genreIds?.mapNotNull { it.toDomainCategory() } ?: emptyList()
    )
}

fun UserGamePointsEntity.toDomain(): UserPoints {
    return UserPoints(
        userId = userId,
        gamePoints = gamePoints
    )
}

fun UserPoints.toEntity(): UserGamePointsEntity {
    return UserGamePointsEntity(
        userId = userId,
        gamePoints = gamePoints
    )
}


private val categoryIdMap = mapOf(
    28 to Category.Action,
    12 to Category.Adventure,
    16 to Category.Animation,
    35 to Category.Comedy,
    80 to Category.Crime,
    99 to Category.Documentary,
    18 to Category.Drama,
    10751 to Category.Family,
    14 to Category.Fantasy,
    36 to Category.History,
    27 to Category.Horror,
    10402 to Category.Music,
    9648 to Category.Mystery,
    10749 to Category.Romance,
    878 to Category.ScienceFiction,
    10770 to Category.TvMovie,
    53 to Category.Thriller,
    10752 to Category.War,
    37 to Category.Western,
    10759 to Category.ActionAdventure,
    10762 to Category.Kids,
    10763 to Category.News,
    10764 to Category.Reality,
    10765 to Category.ScifiFantasy,
    10766 to Category.Soap,
    10767 to Category.Talk,
    10768 to Category.WarPolitics
)

fun Int?.toDomainCategory(): Category {
    return this?.let { categoryIdMap[it] } ?: Category.Unknown
}