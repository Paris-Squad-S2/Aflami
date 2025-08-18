package com.datasource.local.media.datasource

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.datasource.local.media.dao.MediaDao
import com.google.common.truth.Truth.assertThat
import com.repository.media.datasource.local.MediaLocalDataSource
import com.repository.media.models.local.media.Category
import com.repository.media.models.local.media.HomeMediaEntity
import com.repository.media.models.local.media.MediaEntity
import com.repository.media.models.local.media.MediaTypeEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.verify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MediaLocalDataSourceImplTest {
    private val mediaDao: MediaDao = mockk(relaxed = true)
    private val workManager: WorkManager = mockk(relaxed = true)
    private lateinit var dataSource: MediaLocalDataSource

    @BeforeEach
    fun setUp() {
        dataSource = MediaLocalDataSourceImpl(mediaDao, workManager)
    }

    @Test
    fun `addMediaList should insert media list into DAO`() = runTest {
        // When
        dataSource.addHomeMedia(listOf(sampleEntity))

        // Then
        coVerify(exactly = 1) { mediaDao.addHomeMedia(listOf(sampleEntity)) }
    }

    @Test
    fun `addMediaList should enqueue WorkManager request for each distinct category`() = runTest {
        // Given
        val mediaList = listOf(
            sampleEntity,
            sampleEntity.copy(id = 3, category = Category.TOP_RATED)
        )

        // When
        dataSource.addHomeMedia(mediaList)

        // Then
        verify(exactly = 2) { workManager.enqueue(any<OneTimeWorkRequest>()) }
    }

    @Test
    fun `getMediaListByCategory should return list from DAO`() = runTest {
        // Given
        coEvery { mediaDao.getHomeMediaByCategory(Category.UPCOMING,"en") } returns listOf(sampleEntity)

        // When
        val result = dataSource.getHomeMediaByCategory(Category.UPCOMING,"en")

        // Then
        assertThat(result).hasSize(1)
        coVerify { mediaDao.getHomeMediaByCategory(Category.UPCOMING,"en") }
    }

    @Test
    fun `clearMediaByCategory should call DAO method`() = runTest {
        // When
        dataSource.clearHomeMediaByCategory(Category.TOP_RATED)

        // Then
        coVerify(exactly = 1) { mediaDao.clearHomeMediaByCategory(Category.TOP_RATED) }
    }
    @Test
    fun `getAllMedia should return media list when getAllMedia in HomeMediaDao called successfully`() =
        runTest {
            // Given
            coEvery { mediaDao.getMediaContinueWatching() } returns flowOf(listOf(sampleMedia))
            // When
            val result = dataSource.getMediaContinueWatching().first()
            // Then
            assertThat(result).containsExactly(sampleMedia)
        }

    @Test
    fun `getAllMedia should return empty list when HomeMediaDao returns nothing`() =
        runTest {
            // Given
            coEvery { mediaDao.getMediaContinueWatching() } returns flowOf(emptyList())
            // When
            val result = dataSource.getMediaContinueWatching().first()
            // Then
            Assertions.assertTrue(result.isEmpty())
        }

    @Test
    fun `addMedia should add media when addMedia in HomeMediaDao called successfully`() =
        runTest {
            // Given
            coEvery { mediaDao.addMediaContinueWatching(any()) } returns Unit
            // When
            dataSource.addMediaContinueWatching(sampleMedia)
            // Then
            coVerify { mediaDao.addMediaContinueWatching(sampleMedia) }
        }

    companion object{
        private val sampleEntity = HomeMediaEntity(
            id = 1,
            title = "Test Title",
            voteAverage = 8.1,
            posterPath = "poster.jpg",
            releaseDate = "2023-08-01",
            genreIds = listOf(1, 2),
            type = MediaTypeEntity.Movie,
            category = Category.POPULAR,
            language = "en"
        )

        val sampleMedia = MediaEntity(
            id = 1,
            title = "Test Movie",
            voteAverage = 8.5,
            posterPath = "/some/path.jpg",
            releaseDate = "2023-01-01",
            genreIds = listOf(12, 18),
            type = MediaTypeEntity.Movie
        )
    }
}
