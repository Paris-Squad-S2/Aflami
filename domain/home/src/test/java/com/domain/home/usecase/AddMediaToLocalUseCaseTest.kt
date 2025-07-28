package com.domain.home.usecase

import com.domain.home.model.Media
import com.domain.home.model.MediaType
import com.domain.home.repository.MediaRepository
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test
import io.mockk.coVerify
import io.mockk.mockk

class AddMediaToLocalUseCaseTest {
    private val mediaRepository: MediaRepository = mockk(relaxed = true)
    private val addMediaToLocalUseCase = AddMediaToLocalUseCase(mediaRepository)

    @Test
    fun `invoke calls repository addMediaToLocal with given media`() = runTest {
        val media = Media(
            id = 123,
            title = "Some Title",
            voteAverage = 8.5,
            posterPath = "img.jpg",
            yearOfRelease = LocalDate(2023, 1, 1),
            genreIds = listOf(28, 18),
            type = MediaType.MOVIE
        )
        addMediaToLocalUseCase(media)
        coVerify { mediaRepository.addMediaToCountineWatch(media) }
    }
}
