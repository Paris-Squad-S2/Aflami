package com.feature.mediaDetails.mediaDetailsUi.ui.screen.cast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.cast.CastItem
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.CastUi
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel
import kotlin.collections.chunked
import kotlin.collections.forEach


@Composable
fun CastScreen(
    viewModel: CastViewModel = koinViewModel()
) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    CastScreenContent(state = state.value)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastScreenContent(
    state: CastUiState,
    ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .navigationBarsPadding()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            TopAppBar(
                title = "Cast",
                leadingIcons = listOf(
                    iconItemWithDefaults(
                        icon = ImageVector.vectorResource(R.drawable.ic_back),
                        onClick = {}
                    )
                )
            )
        }
        items(state.cast.chunked(3)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { cast ->
                    CastItem(
                        imageUrl = cast.imageUrl,
                        name = cast.name,
                        modifier = Modifier.weight(1f)
                    )
                }
                repeat(3 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


fun fakeCastUiState(): CastUiState {
    return CastUiState(
        cast = listOf(
            CastUi(
                name = "Tom Hanks",
                imageUrl = "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg"
            ),
            CastUi(
                name = "Michael Clarke Duncan",
                imageUrl = "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg"
            ),
            CastUi(
                name = "Michael Clarke Duncan",
                imageUrl = "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg"
            ),
            CastUi(
                name = "Michael Clarke Duncan",
                imageUrl = "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg"
            )
        ),
        isLoading = false,
        errorMessage = null
    )
}

@PreviewLightDark
@Composable
fun CastScreenPreview() {
    CastScreenContent(
        state = fakeCastUiState()
    )
}
