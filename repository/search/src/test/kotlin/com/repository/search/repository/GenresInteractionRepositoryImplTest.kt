package com.repository.search.repository

import com.domain.search.model.GenreUserInteractionModel
import com.repository.search.dataSource.local.GenresInteractionDataSource
import com.repository.search.entity.GenreUserInteractionEntity
import com.repository.search.mapper.toCategoryUserInteractionEntity
import com.repository.search.mapper.toCategoryUserInteractionModel
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GenresInteractionRepositoryImplTest {
    private lateinit var dataSource: GenresInteractionDataSource
    private lateinit var repository: GenresInteractionRepositoryImpl

    @BeforeEach
    fun setUp() {
        dataSource = mockk(relaxed = true)
        repository = GenresInteractionRepositoryImpl(dataSource)
        mockkStatic("com.repository.search.mapper.CategoryInteractionMapperKt")
    }

    @Test
    fun `upsertInteraction should call dataSource with mapped entity`() = runTest {
        val model = GenreUserInteractionModel(genreId = 3, interactionCount = 7)
        val entity = GenreUserInteractionEntity(genreId = 3, interactionCount = 7)
        coJustRun { dataSource.upsertInteraction(entity) }
        every { model.toCategoryUserInteractionEntity() } returns entity

        repository.upsertInteraction(model)

        coVerify { dataSource.upsertInteraction(entity) }
    }

    @Test
    fun `getCategoryInteractions should return dataSource value if found`() = runTest {
        coEvery { dataSource.getCategoryInteractions(12) } returns 2
        val result = repository.getCategoryInteractions(12)
        assertEquals(2, result)
    }

    @Test
    fun `getCategoryInteractions should return null if not found`() = runTest {
        coEvery { dataSource.getCategoryInteractions(44) } returns null
        val result = repository.getCategoryInteractions(44)
        assertNull(result)
    }

    @Test
    fun `getAllInteractions should map and return model list from dataSource`() = runTest {
        val entities = listOf(
            GenreUserInteractionEntity(1, 8),
            GenreUserInteractionEntity(2, 3)
        )
        val models = listOf(
            GenreUserInteractionModel(1, 8),
            GenreUserInteractionModel(2, 3)
        )
        coEvery { dataSource.getAllInteractions() } returns entities
        every { entities[0].toCategoryUserInteractionModel() } returns models[0]
        every { entities[1].toCategoryUserInteractionModel() } returns models[1]
        val result = repository.getAllInteractions()
        assertEquals(models, result)
    }

    @Test
    fun `getAllInteractions should return empty list if dataSource returns empty`() = runTest {
        coEvery { dataSource.getAllInteractions() } returns emptyList()
        val result = repository.getAllInteractions()
        assertEquals(emptyList(), result)
    }
}
