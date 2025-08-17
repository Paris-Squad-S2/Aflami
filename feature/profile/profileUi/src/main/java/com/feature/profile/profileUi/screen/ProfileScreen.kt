package com.feature.profile.profileUi.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.profile.profileUi.R
import com.feature.profile.profileUi.screen.components.AppLanguageDialog
import com.feature.profile.profileUi.screen.components.AppLogOutDialog
import com.feature.profile.profileUi.screen.components.AppRestrictionDialog
import com.feature.profile.profileUi.screen.components.AppSettingDialog
import com.feature.profile.profileUi.screen.components.AppThemeDialog
import com.feature.profile.profileUi.screen.components.ProfileDetails
import com.feature.profile.profileUi.screen.components.ProfileHeader
import com.feature.profile.profileUi.screen.components.ProfileSetUp
import com.paris_2.aflami.designsystem.components.AppHorizontalDivider
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CategoryCard
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.theme.Theme


@Composable
fun ProfileScreen(modifier: Modifier = Modifier, viewModel: ProfileViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    if (state.value.isLogin)
        ProfileContent(
            modifier = modifier,
            state = state.value,
            profileInteractionListener = viewModel,
            context = context
        )
    else
        LoggedOutContent(
            modifier = modifier,
            profileInteractionListener = viewModel,
        )
}

@Composable
fun LoggedOutContent(
    modifier: Modifier,
    profileInteractionListener: ProfileViewModel,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_profile_image),
            contentDescription = null,
        )
        AppText(
            text = stringResource(R.string.please_login_to_access_your_account_details_and_other_features),
            style = Theme.textStyle.body.small,
            color = Theme.colors.text.body,
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 48.dp),
            textAlign = TextAlign.Center
        )
        CustomButton(
            onClick = profileInteractionListener::onLogoutApplyClicked,
            text = com.paris_2.aflami.designsystem.R.string.login,
            type = ButtonType.Secondary,
            modifier = Modifier.padding(top = 24.dp)
        )

    }
}

@Composable
fun ProfileContent(
    state: ProfileScreenUiState,
    profileInteractionListener: InterActionListener,
    modifier: Modifier = Modifier,
    context: Context,
) {

    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        ProfileHeader(modifier = Modifier)
        Column(
            modifier = Modifier
                .offset(y = (-56).dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfileDetails(modifier, state.profile.name,state.profile.points)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp)
            ) {
                CategoryCard(
                    stringResource(R.string.watch_history),
                    painterResource(R.drawable.ic_clock_3d),
                    onCategoryClick = profileInteractionListener::onWatchHistoryClicked,
                    modifier = Modifier.weight(1F)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CategoryCard(
                    categoryName = stringResource(R.string.my_rating),
                    categoryImage = painterResource(R.drawable.ic_star_3d),
                    onCategoryClick = profileInteractionListener::onMyRatingClicked,
                    modifier = Modifier.weight(1F)
                )
            }
            AppHorizontalDivider(
                thickness = 1.dp,
                color = Theme.colors.stroke,
                modifier = Modifier.padding(vertical = 24.dp)
            )
            ProfileSetUp(
                interactionListener = profileInteractionListener,
                typeMode = state.profile.theme.name
            )
        }


        AppLanguageDialog(
            isVisible = state.profile.isLanguageDialogOpen,
            onDismiss = profileInteractionListener::onDismissLanguageDialog,
            languageState = state.profile.language,
            onLanguageSelected = profileInteractionListener::onLanguageApplyClicked

        )


        AppThemeDialog(
            isVisible = state.profile.isThemeDialogOpen,
            onDismiss = profileInteractionListener::onDismissAppearanceDialog,
            onThemeSelected = { appearance ->
                profileInteractionListener.onAppearanceApplyClicked(appearance)
                val intent =
                    context.packageManager.getLaunchIntentForPackage(context.packageName)
                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context.startActivities(arrayOf(intent))
            },
            themeState = state.profile.theme
        )
        AppSettingDialog(
            isVisible = state.profile.isSettingDialogOpen,
            onDismiss = profileInteractionListener::onDismissSettingDialog,
            isLogoutDialogVisible = profileInteractionListener::onLogoutClicked,
            isRestrictionDialogVisible = profileInteractionListener::onContentRestrictionClicked
        )
        AppLogOutDialog(
            isVisible = state.profile.isLogoutDialogOpen,
            onDismiss = profileInteractionListener::onDismissLogoutDialog,
            onLogout = profileInteractionListener::onLogoutApplyClicked
        )
        AppRestrictionDialog(
            isVisible = state.profile.isContentRestrictionDialogOpen,
            onDismiss = profileInteractionListener::onDismissContentRestrictionDialog,
            onSave = profileInteractionListener::onRestrictionSelected,
            restriction = state.profile.contentRestriction
        )
    }
}