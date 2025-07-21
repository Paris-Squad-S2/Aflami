package com.feature.authentication.authenticationUi.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.authentication.authenticationUi.R
import com.feature.authentication.authenticationUi.screen.login.components.CircleBackground
import com.feature.authentication.authenticationUi.screen.login.components.HeaderIconLogin
import com.paris_2.aflami.designsystem.components.AflamiButton
import com.paris_2.aflami.designsystem.components.AflamiText
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.TextField
import com.paris_2.aflami.designsystem.theme.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = koinViewModel()) {
    val uiState = viewModel.screenState.collectAsStateWithLifecycle()
    LoginScreenContent(uiState.value, viewModel)
}

@Composable
fun LoginScreenContent(
    loginUIState: LoginUIState,
    loginScreenInteractionListener: LoginScreenInteractionListener
) {
    val scrollVerticalState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFD85895).copy(alpha = 0.24f),
                        Color(0xFFD85895).copy(alpha = 0f)
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(scrollVerticalState)
    ) {

        CircleBackground(
            xPercentage = -0.09f,
            yPercentage = 0.35f,
            size = 100.dp
        )
        CircleBackground(
            xPercentage = 0.70f,
            yPercentage = 0.41f,
            size = 64.dp
        )
        CircleBackground(
            xPercentage = 0.90f,
            yPercentage = 0.60f,
            size = 64.dp
        )
        CircleBackground(
            xPercentage = 0.5f,
            yPercentage = 0.70f,
            size = 32.dp
        )

        CircleBackground(
            xPercentage = 0.05f,
            yPercentage = 0.78f,
            size = 24.dp
        )

        CircleBackground(
            xPercentage = 0.68f,
            yPercentage = 0.85f,
            size = 24.dp
        )
        Column(modifier = Modifier.fillMaxSize()) {

            HeaderIconLogin(modifier = Modifier.padding(top = 24.dp, start = 12.dp))

            AflamiText(
                text = stringResource(R.string.welcome_back),
                style = Theme.textStyle.title.medium,
                color = Theme.colors.text.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 12.dp)
            )

            AflamiText(
                text = stringResource(R.string.please_enter_your_information_to_login),
                style = Theme.textStyle.body.medium,
                color = Theme.colors.text.body,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(horizontal = 12.dp)
            )

            TextField(
                value = loginUIState.username,
                onValueChange = loginScreenInteractionListener::onUsernameChange,
                placeholder = stringResource(R.string.user_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                leadingIcon = R.drawable.ic_user,
            )

            TextField(
                value = loginUIState.password,
                onValueChange = loginScreenInteractionListener::onPasswordChange,
                placeholder = stringResource(R.string.password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                leadingIcon = R.drawable.ic_password,
                trailingIcon = if (loginUIState.showPassword) R.drawable.ic_eye else R.drawable.ic_eye,
                onClickTrailingIcon = {
                    loginScreenInteractionListener.onShowPasswordChange(
                        loginUIState.showPassword
                    )
                },
                showError = loginUIState.isErrorPassword,
                errorMessage = R.string.incorrect_password,
                showText = loginUIState.showPassword
            )
            AflamiText(
                text = stringResource(R.string.forgot_password),
                style = Theme.textStyle.label.medium,
                color = Theme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 48.dp)
                    .padding(horizontal = 12.dp)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = loginScreenInteractionListener::onClickForgotPassword
                    ),
                textAlign = TextAlign.End
            )


            AflamiButton(
                onClick = loginScreenInteractionListener::onClickLogin,
                type = ButtonType.Primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 12.dp),
                text = R.string.login,
                state = loginUIState.buttonState
            )
            Spacer(modifier = Modifier.padding(top = 12.dp))
            AflamiButton(
                onClick = loginScreenInteractionListener::onClickLoginAsGuest,
                type = ButtonType.Secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 12.dp),
                text = R.string.continue_as_guest,
            )
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                AflamiText(
                    text = stringResource(R.string.don_t_have_account),
                    style = Theme.textStyle.label.medium,
                    color = Theme.colors.text.hint,
                    modifier = Modifier.padding(end = 4.dp),
                    textAlign = TextAlign.Center
                )
                AflamiText(
                    text = stringResource(R.string.create_account),
                    style = Theme.textStyle.label.medium,
                    color = Theme.colors.primary,
                    modifier = Modifier
                        .clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = loginScreenInteractionListener::onClickCreateAccount
                        ),
                    textAlign = TextAlign.Center
                )
            }

        }
        CircleBackground(
            xPercentage = 0.55f,
            yPercentage = -0.09f,
            size = 64.dp
        )

        CircleBackground(
            xPercentage = 0.1f,
            yPercentage = -0.09f,
            size = 32.dp
        )


        CircleBackground(
            xPercentage = 0.95f,
            yPercentage = -0.04f,
            size = 40.dp
        )

        CircleBackground(
            xPercentage = 0.88f,
            yPercentage = 0.10f,
            size = 64.dp
        )

        CircleBackground(
            xPercentage = -0.09f,
            yPercentage = 0.88f,
            size = 100.dp
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenContentPreview() {
    val uiState = MutableStateFlow(LoginUIState())

    LoginScreenContent(
        uiState.collectAsStateWithLifecycle().value,
        object : LoginScreenInteractionListener {
            override fun onUsernameChange(username: String) {
                uiState.update { it.copy(username = username) }
            }

            override fun onPasswordChange(password: String) {
                uiState.update { it.copy(password = password) }

            }

            override fun onShowPasswordChange(showPassword: Boolean) {
                uiState.update { it.copy(showPassword = !showPassword) }
            }

            override fun onClickLogin() {
                TODO("Not yet implemented")
            }

            override fun onClickLoginAsGuest() {
                TODO("Not yet implemented")
            }

            override fun onClickForgotPassword() {
                TODO("Not yet implemented")
            }

            override fun onClickCreateAccount() {
                TODO("Not yet implemented")
            }

        })
}