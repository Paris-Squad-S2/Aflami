package com.feature.categories.categoriesUi.screen.categories

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CategoriesScreenViewModelTest {

    private lateinit var viewModel: CategoriesScreenViewModel

    @BeforeEach
    fun setup() {
        viewModel = CategoriesScreenViewModel()
    }

    @Test
    fun `onSelectTab should update selectedTabIndex`() = runTest {
        // Given
        viewModel.onSelectTab(2)

        // Then
        val newIndex = viewModel.screenState.value.categoriesUIState.selectedTabIndex
        assertThat(newIndex).isEqualTo(2)
    }
}