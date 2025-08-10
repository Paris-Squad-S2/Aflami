package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.repository.MediaRepository
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test
import io.mockk.coVerify
import io.mockk.mockk

class AddWatchHistoryUseCaseTest {
    private val mediaRepository: MediaRepository = mockk(relaxed = true)
    private val addWatchHistoryUseCase = AddWatchHistoryUseCase(mediaRepository)

    @Test
    fun `invoke calls repository addMediaToLocal with given media`() = runTest {
        val media = Media(
            id = 123,
            title = "Some Title",
            rating = 8.5,
            imageUri = "img.jpg",
            yearOfRelease = LocalDate(2023, 1, 1),
            categories = listOf(Category.Action, Category.Adventure),
            type = MediaType.Movie
        )
        addWatchHistoryUseCase(media)
        coVerify { mediaRepository.addMediaToContinueWatching(media) }
    }
}
