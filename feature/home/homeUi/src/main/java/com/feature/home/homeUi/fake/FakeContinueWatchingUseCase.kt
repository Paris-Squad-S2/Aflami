package com.feature.home.homeUi.fake

import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import kotlinx.datetime.LocalDate

class FakeContinueWatchingUseCase {
     operator fun invoke(): List<MediaUiState>{
        return mediaList
    }
}

val mediaList = listOf(
    MediaUiState(
        id = 1,
        imageUri = "https://image.tmdb.org/t/p/w500/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg", // The Batman
        title = "The Batman",
        type = MediaTypeUi.MOVIE,
        categories = listOf(28, 80, 18), // Action, Crime, Drama
        yearOfRelease = LocalDate(2022, 3, 4),
        rating = 7.8
    ),
    MediaUiState(
        id = 2,
        imageUri = "https://image.tmdb.org/t/p/w500/8YFL5QQVPy3AgrEQxNYVSgiPEbe.jpg", // Spider-Man: No Way Home
        title = "Spider-Man: No Way Home",
        type = MediaTypeUi.MOVIE,
        categories = listOf(28, 12, 14), // Action, Adventure, Fantasy
        yearOfRelease = LocalDate(2021, 12, 17),
        rating = 8.2
    ),
    MediaUiState(
        id = 3,
        imageUri = "https://image.tmdb.org/t/p/w500/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg", // Avatar: The Way of Water
        title = "Avatar: The Way of Water",
        type = MediaTypeUi.MOVIE,
        categories = listOf(878, 12), // Sci-Fi, Adventure
        yearOfRelease = LocalDate(2022, 12, 16),
        rating = 7.6
    ),
    MediaUiState(
        id = 4,
        imageUri = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg", // Inception
        title = "Inception",
        type = MediaTypeUi.MOVIE,
        categories = listOf(28, 878, 53), // Action, Sci-Fi, Thriller
        yearOfRelease = LocalDate(2010, 7, 16),
        rating = 8.8
    ),
    MediaUiState(
        id = 5,
        imageUri = "https://image.tmdb.org/t/p/w500/t/p/original/xqvyhX5yJ2xSmNia6GgUwkkVdbu.jpg", // Interstellar
        title = "Interstellar",
        type = MediaTypeUi.MOVIE,
        categories = listOf(12, 18, 878), // Adventure, Drama, Sci-Fi
        yearOfRelease = LocalDate(2014, 11, 7),
        rating = 8.6
    )
)
