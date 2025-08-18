package com.repository.guessgame.mapper

import com.paris_2.domain.game.entity.Actor
import com.paris_2.domain.game.entity.ActorMedia
import com.paris_2.domain.game.entity.UserPoints
import com.paris_2.domain.game.utils.Genre
import com.repository.guessgame.dto.ActorDto
import com.repository.guessgame.dto.ActorMediaDto
import com.repository.guessgame.entity.UserGamePointsEntity
import kotlinx.datetime.LocalDate

fun ActorDto.toDomain(): Actor? {
    return Actor(
        id = id ?: -1,
        name = name ?: "",
        imageUri = profilePath ?: "",
        media = knownFor?.mapNotNull { it?.toDomain() } ?: emptyList()
    )
}

fun ActorMediaDto.toDomain(): ActorMedia? {
    val dateString = releaseDate ?: firstAirDate
    val parsedDate = dateString?.let {
        runCatching { LocalDate.parse(it) }.getOrNull()
    } ?: return null
    return ActorMedia(
        id = id ?: -1,
        name = title ?: name ?: originalTitle ?: originalName ?: "",
        posterImg = posterPath ?: "",
        yearOfRelease = parsedDate,
        genres = genreIds?.mapNotNull { it.toDomainGenre() } ?: emptyList()
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


private val genreIdMap = mapOf(
    28 to Genre.ACTION,
    12 to Genre.ADVENTURE,
    16 to Genre.ANIMATION,
    35 to Genre.COMEDY,
    80 to Genre.CRIME,
    99 to Genre.DOCUMENTARY,
    18 to Genre.DRAMA,
    10751 to Genre.FAMILY,
    14 to Genre.FANTASY,
    36 to Genre.HISTORY,
    27 to Genre.HORROR,
    10402 to Genre.MUSIC,
    9648 to Genre.MYSTERY,
    10749 to Genre.ROMANCE,
    878 to Genre.SCIENCE_FICTION,
    10770 to Genre.TV_MOVIE,
    53 to Genre.THRILLER,
    10752 to Genre.WAR,
    37 to Genre.WESTERN,
    10759 to Genre.ACTION_ADVENTURE,
    10762 to Genre.KIDS,
    10763 to Genre.NEWS,
    10764 to Genre.REALITY,
    10765 to Genre.SCI_FI_FANTASY,
    10766 to Genre.SOAP,
    10767 to Genre.TALK,
    10768 to Genre.WAR_POLITICS
)

fun Int?.toDomainGenre(): Genre {
    return this?.let { genreIdMap[it] } ?: Genre.UNKNOWN
}
