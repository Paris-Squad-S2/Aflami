package com.repository.guessgame.mapper

import com.paris_2.domain.game.entity.Actor
import com.paris_2.domain.game.entity.ActorMedia
import com.paris_2.domain.game.entity.UserPoints
import com.repository.guessgame.dto.ActorDto
import com.repository.guessgame.dto.ActorMediaDto
import com.repository.guessgame.entity.UserGamePointsEntity
import kotlinx.datetime.LocalDate

fun ActorDto.toDomain() : Actor? {
    return Actor(
      id = id ?: -1,
        name = originalName ?: "",
        imageUri = profilePath ?: "",
        media = knownFor?.mapNotNull { it?.toDomain() } ?: emptyList()
    )
}

fun ActorMediaDto.toDomain() : ActorMedia?{
    val dateString = releaseDate ?: firstAirDate
    val parsedDate = dateString?.let {
        runCatching { LocalDate.parse(it) }.getOrNull()
    } ?: return null
    return ActorMedia(
        id = id ?: -1,
        name = title ?: name ?: originalTitle ?: originalName ?: "",
        posterImg = posterPath ?: "",
        yearOfRelease = parsedDate,
        genres = genreIds?.mapNotNull { it } ?: emptyList()
    )
}

fun UserGamePointsEntity.toDomain() : UserPoints{
    return UserPoints(
        userId = userId,
        gamePoints = gamePoints
    )
}

fun UserPoints.toEntity() : UserGamePointsEntity{
    return UserGamePointsEntity(
        userId = userId,
        gamePoints = gamePoints
    )
}