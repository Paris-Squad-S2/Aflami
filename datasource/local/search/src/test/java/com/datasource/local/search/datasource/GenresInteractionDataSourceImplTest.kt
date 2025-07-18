package com.datasource.local.search.datasource

import com.datasource.local.search.dao.GenresUserInteractionDao
import com.google.common.truth.Truth.assertThat
import com.repository.search.entity.GenreUserInteractionEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GenresInteractionDataSourceImplTest {
    private lateinit var genresInteractionDataSource: GenresInteractionDataSourceImpl
    private val genresUserInteractionDao: GenresUserInteractionDao = mockk(relaxed = false)
    private lateinit var sampleInteraction: GenreUserInteractionEntity

    @BeforeEach
    fun setUp() {
        genresInteractionDataSource = GenresInteractionDataSourceImpl(genresUserInteractionDao)
        sampleInteraction = GenreUserInteractionEntity(
            genreId = 5,
            interactionCount = 9
        )
    }

    @Test
    fun `upsertInteraction should call DAO with correct entity`() = runTest {
        // Given
        coEvery { genresUserInteractionDao.upsertInteraction(any()) } returns Unit
        // When
        genresInteractionDataSource.upsertInteraction(sampleInteraction)
        // Then
        coVerify { genresUserInteractionDao.upsertInteraction(sampleInteraction) }
    }

    @Test
    fun `getCategoryInteractions should return correct count when entry exists`() = runTest {
        // Given
        coEvery { genresUserInteractionDao.getCategoryInteractions(sampleInteraction.genreId) } returns sampleInteraction.interactionCount
        // When
        val result = genresInteractionDataSource.getCategoryInteractions(sampleInteraction.genreId)
        // Then
        assertThat(result).isEqualTo(9)
    }

    @Test
    fun `getCategoryInteractions should return null when no entry exists`() = runTest {
        // Given
        coEvery { genresUserInteractionDao.getCategoryInteractions(888) } returns null
        // When
        val result = genresInteractionDataSource.getCategoryInteractions(888)
        // Then
        assertThat(result).isNull()
    }

    @Test
    fun `getAllInteractions should return all interactions from DAO`() = runTest {
        // Given
        val list = listOf(
            GenreUserInteractionEntity(1, 2),
            GenreUserInteractionEntity(2, 4)
        )
        coEvery { genresUserInteractionDao.getAllInteractions() } returns list
        // When
        val result = genresInteractionDataSource.getAllInteractions()
        // Then
        assertThat(result).containsExactlyElementsIn(list)
    }

    @Test
    fun `getAllInteractions should return empty list when DAO returns none`() = runTest {
        // Given
        coEvery { genresUserInteractionDao.getAllInteractions() } returns emptyList()
        // When
        val result = genresInteractionDataSource.getAllInteractions()
        // Then
        assertThat(result).isEmpty()
    }
}
