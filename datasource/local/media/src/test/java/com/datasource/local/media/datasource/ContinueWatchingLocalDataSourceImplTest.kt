package com.datasource.local.media.datasource

import com.datasource.local.media.dao.ContinueWatchingDao
import com.google.common.truth.Truth.assertThat
import com.repository.media.entity.MediaEntity
import com.repository.media.entity.MediaTypeEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ContinueWatchingLocalDataSourceImplTest {
    private lateinit var continueWatchingLocalDataSource: ContinueWatchingLocalDataSourceImpl
    private val mediaDao: ContinueWatchingDao = mockk(relaxed = false)

    @BeforeEach
    fun setUp() {
        continueWatchingLocalDataSource = ContinueWatchingLocalDataSourceImpl(mediaDao)
    }

    @Test
    fun `getAllMedia should return media list when getAllMedia in HomeMediaDao called successfully`() =
        runTest {
            // Given
            coEvery { mediaDao.getMediaContinueWatching() } returns listOf(sampleMedia)
            // When
            val result = continueWatchingLocalDataSource.getAllMedia()
            // Then
            assertThat(result).containsExactly(sampleMedia)
        }

    @Test
    fun `getAllMedia should return empty list when HomeMediaDao returns nothing`() =
        runTest {
            // Given
            coEvery { mediaDao.getMediaContinueWatching() } returns emptyList()
            // When
            val result = continueWatchingLocalDataSource.getAllMedia()
            // Then
            Assertions.assertTrue(result.isEmpty())
        }

    @Test
    fun `addMedia should add media when addMedia in HomeMediaDao called successfully`() =
        runTest {
            // Given
            coEvery { mediaDao.addMediaContinueWatching(any()) } returns Unit
            // When
            continueWatchingLocalDataSource.addMedia(sampleMedia)
            // Then
            coVerify { mediaDao.addMediaContinueWatching(sampleMedia) }
        }

    private companion object {
        val sampleMedia = MediaEntity(
            id = 1,
            title = "Test Movie",
            voteAverage = 8.5,
            posterPath = "/some/path.jpg",
            releaseDate = "2023-01-01",
            genreIds = listOf(12, 18),
            type = MediaTypeEntity.MOVIE
        )
    }

}