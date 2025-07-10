package com.feature.search.searchUi.screen.findByActor

import com.domain.search.useCases.AddRecentSearchUseCase
import com.domain.search.useCases.GetMediaByActorNameUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FindByActorViewModelTest {
    private lateinit var viewModel: FindByActorViewModel
    private lateinit var getMediaByActorNameUseCase: GetMediaByActorNameUseCase
    private lateinit var addRecentSearchesUseCase: AddRecentSearchUseCase

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getMediaByActorNameUseCase = mockk(relaxed = true)
        addRecentSearchesUseCase = mockk(relaxed = true)
        viewModel = FindByActorViewModel(
            getMediaByActorNameUseCase = getMediaByActorNameUseCase,
            addRecentSearchesUseCase = addRecentSearchesUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be correct`() {
        val initialState = viewModel.screenState.value

        assertThat(initialState.uiState.searchQuery).isEqualTo("")
        assertThat(initialState.uiState.searchResult).isEmpty()
        assertThat(initialState.isLoading).isFalse()
        assertThat(initialState.errorMessage).isNull()
    }

}