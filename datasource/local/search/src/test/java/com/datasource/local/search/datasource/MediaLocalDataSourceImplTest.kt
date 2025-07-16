package com.datasource.local.search.datasource

import com.datasource.local.search.dao.MediaDao
import com.google.common.truth.Truth.assertThat
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.MediaTypeEntity
import com.repository.search.entity.SearchType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MediaLocalDataSourceImplTest {
    private lateinit var mediaLocalDataSourceImpl: MediaLocalDataSourceImpl
    private val mediaDao: MediaDao = mockk(relaxed = true)

    private lateinit var sampleMedia: MediaEntity

    @BeforeEach
    fun setUp() {
        mediaLocalDataSourceImpl = MediaLocalDataSourceImpl(mediaDao)
        sampleMedia = MediaEntity(
            id = 0,
            searchQuery = "aa",
            imageUri = "www.image.com",
            title = "batman",
            type = MediaTypeEntity.MOVIE,
            category = listOf(1, 2),
            yearOfRelease = LocalDate.parse("2023-01-01"),
            rating = 4.5,
            searchType = SearchType.Query
        )
    }

    @Test
    fun `getAllMedia should return media when getAll in MediaDao called successfully`() =
        runTest {
            // Given
            coEvery { mediaDao.getAllMedia() } returns listOf(sampleMedia)
            // When
            val result = mediaLocalDataSourceImpl.getAllMedia()
            // Then
            assertThat(result).containsExactly(sampleMedia)
        }

    @Test
    fun `addAllMedia should add AllMedia when add in MediaDao called successfully`() =
        runTest {
            // Given

            coEvery { mediaDao.addAllMedia(any()) } returns Unit
            // When
            mediaLocalDataSourceImpl.addAllMedia(listOf(sampleMedia))
            // Then
            coVerify {
                mediaDao.addAllMedia(withArg {
                    assertThat(it).containsExactly(sampleMedia)
                })
            }
        }

    @Test
    fun `getCachedMedia should get CachedMedia when get in MediaDao called successfully`() =
        runTest {
            // Given
            coEvery { mediaDao.getCachedMedia() } returns listOf(sampleMedia)
            // When
            val result = mediaLocalDataSourceImpl.getCachedMedia()
            // Then
            assertThat(result).containsExactly(sampleMedia)
        }

    @Test
    fun `getMediaByActor should get MediaByActor when get in MediaDao called successfully`() =
        runTest {
            // Given
            coEvery { mediaDao.getMediaByActor("aaa") } returns listOf(sampleMedia)
            // When
            val result = mediaLocalDataSourceImpl.getMediaByActor("aaa")
            // Then
            assertThat(result).containsExactly(sampleMedia)
        }

    @Test
    fun `getMediaByCountry should get MediaByCountry when get in MediaDao called successfully`() =
        runTest {
            // Given
            coEvery { mediaDao.getMediaByCountry("usa") } returns listOf(sampleMedia)
            // When
            val result = mediaLocalDataSourceImpl.getMediaByCountry("usa")
            // Then
            assertThat(result).containsExactly(sampleMedia)
        }

    @Test
    fun `getMediaByTitleQuery should get MediaByTitleQuery when get in MediaDao called successfully`() =
        runTest {
            // Given
            coEvery { mediaDao.getMediaByTitleQuery("batman") } returns listOf(sampleMedia)
            // When
            val result = mediaLocalDataSourceImpl.getMediaByTitleQuery("batman")
            // Then
            assertThat(result).containsExactly(sampleMedia)
        }

    @Test
    fun `clearAllMediaBySearchQuery should clear AllMediaBySearchQuery when clear in MediaDao called successfully`() =
        runTest {
            // Given
            coEvery { mediaDao.clearAllMediaBySearchQuery("aa", SearchType.Query) } returns Unit
            // When
            mediaLocalDataSourceImpl.clearAllMediaBySearchQuery("aa", SearchType.Query)
            // Then
            coVerify { mediaDao.clearAllMediaBySearchQuery("aa", SearchType.Query) }
        }

}