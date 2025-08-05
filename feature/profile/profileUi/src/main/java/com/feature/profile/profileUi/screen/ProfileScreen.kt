package com.feature.profile.profileUi.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.profile.profileUi.R
import com.feature.profile.profileUi.screen.component.AppLanguageDialog
import com.feature.profile.profileUi.screen.component.ProfileDetails
import com.feature.profile.profileUi.screen.component.ProfileHeader
import com.feature.profile.profileUi.screen.component.ProfileSetUp
import com.paris_2.aflami.designsystem.components.AppHorizontalDivider
import com.paris_2.aflami.designsystem.components.CategoryCard
import com.paris_2.aflami.designsystem.theme.Theme


@Composable
fun ProfileScreen(modifier: Modifier = Modifier, viewModel: ProfileViewModel = hiltViewModel()) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    ProfileContent(modifier = modifier, state = state.value, profileInteractionListener = viewModel)
}

@Composable
fun ProfileContent(
    state: ProfileScreenUiState,
    profileInteractionListener: InterActionListener,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        ProfileHeader(modifier = Modifier)
        Column(
            modifier = Modifier
                .offset(y = (-56).dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfileDetails(modifier)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp)
            ) {
                CategoryCard(
                    stringResource(R.string.watch_history),
                    painterResource(R.drawable.ic_clock_3d),
                    onCategoryClick = { },
                    modifier = Modifier.weight(1F)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CategoryCard(
                    categoryName = stringResource(R.string.my_rating),
                    categoryImage = painterResource(R.drawable.ic_star_3d),
                    onCategoryClick = { },
                    modifier = Modifier.weight(1F)
                )
            }
            AppHorizontalDivider(
                thickness = 1.dp,
                color = Theme.colors.stroke,
                modifier = Modifier.padding(vertical = 24.dp)
            )
            ProfileSetUp(interactionListener = profileInteractionListener)
        }


        AppLanguageDialog(
            isVisible = state.profile.isLanguageDialogOpen,
            onDismiss = profileInteractionListener::onDismissLanguageDialog,
            languageState = state.profile.language,
            onLanguageSelected = profileInteractionListener::onLanguageSelected
        )


    }
}