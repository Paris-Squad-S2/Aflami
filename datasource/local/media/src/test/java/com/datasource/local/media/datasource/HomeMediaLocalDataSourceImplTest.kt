package com.datasource.local.media.datasource

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.datasource.local.media.dao.HomeMediaDao
import com.google.common.truth.Truth.assertThat
import com.repository.media.datasource.local.HomeMediaLocalDataSource
import com.repository.media.entity.Category
import com.repository.media.entity.HomeMediaEntity
import com.repository.media.entity.MediaTypeEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.verify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HomeMediaLocalDataSourceImplTest {
    private val homeMediaDao: HomeMediaDao = mockk(relaxed = true)
    private val workManager: WorkManager = mockk(relaxed = true)
    private lateinit var dataSource: HomeMediaLocalDataSource

    @BeforeEach
    fun setUp() {
        dataSource = HomeMediaLocalDataSourceImpl(homeMediaDao, workManager)
    }

    @Test
    fun `addMediaList should insert media list into DAO`() = runTest {
        // When
        dataSource.addHomeMedia(listOf(sampleEntity))

        // Then
        coVerify(exactly = 1) { homeMediaDao.addHomeMedia(listOf(sampleEntity)) }
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
        coEvery { homeMediaDao.getHomeMediaByCategory(Category.UPCOMING,"en") } returns listOf(sampleEntity)

        // When
        val result = dataSource.getHomeMediaByCategory(Category.UPCOMING,"en")

        // Then
        assertThat(result).hasSize(1)
        coVerify { homeMediaDao.getHomeMediaByCategory(Category.UPCOMING,"en") }
    }

    @Test
    fun `clearMediaByCategory should call DAO method`() = runTest {
        // When
        dataSource.clearHomeMediaByCategory(Category.TOP_RATED)

        // Then
        coVerify(exactly = 1) { homeMediaDao.clearHomeMediaByCategory(Category.TOP_RATED) }
    }

    companion object{
        private val sampleEntity = HomeMediaEntity(
            id = 1,
            title = "Test Title",
            voteAverage = 8.1,
            posterPath = "poster.jpg",
            releaseDate = "2023-08-01",
            genreIds = listOf(1, 2),
            type = MediaTypeEntity.MOVIE,
            category = Category.POPULAR,
            language = "en"
        )
    }
}
