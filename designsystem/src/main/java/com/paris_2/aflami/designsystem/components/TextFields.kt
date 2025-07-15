package com.paris_2.aflami.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    showError: Boolean = false,
    errorMessage: Int? = null,
    onClick: () -> Unit = {},
    onClickTrailingIcon: () -> Unit = { },
    showText: Boolean = true,
    suggestions: List<String> = emptyList(),
    onSuggestionSelected: (String) -> Unit = {}
) {

    var expanded by remember { mutableStateOf(false) }
    val filteredSuggestions = remember(value, suggestions) {
        if (value.isEmpty()) emptyList()
        else suggestions
    }

    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is FocusInteraction.Focus -> isFocused = true
                is FocusInteraction.Unfocus -> isFocused = false
            }
        }
    }

    val borderColor = when {
        isFocused -> Theme.colors.primary
        showError -> Theme.colors.status.redAccent
        else -> Theme.colors.stroke
    }

    val textColor = if (isFocused) {
        Theme.colors.text.body
    } else {
        Theme.colors.text.hint
    }

    val textStyle = if (isFocused) {
        Theme.textStyle.label.medium
    } else {
        Theme.textStyle.body.medium
    }

    val iconColor = if (isFocused) Theme.colors.text.body else Theme.colors.text.hint
    val borderWidth = 1.dp
    val shape = RoundedCornerShape(16.dp)
    val separatorColor = Theme.colors.stroke

    Column(modifier = modifier.fillMaxWidth()) {
        AnimatedVisibility(
            visible = showError,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, start = 16.dp),
            ) {
                Text(
                    text = stringResource(id = errorMessage ?: R.string.error),
                    color = Theme.colors.onPrimaryColors.onPrimary,
                    style = Theme.textStyle.label.medium,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .background(
                            color = Theme.colors.status.redAccent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        )
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(4.dp)
                        .width(8.dp)
                        .offset(y = (-0.2).dp)
                )
            }
            if (showError) {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        BasicTextField(
            value = value,
            enabled = enabled,
            visualTransformation = if (showText) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = { newText ->
                if (singleLine) {
                    if (newText.length <= 100) {
                        onValueChange(newText)
                        expanded = true
                    }
                } else {
                    if (newText.lines().size <= 11) {
                        onValueChange(newText)
                        expanded = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(if (singleLine) 56.dp else 168.dp)
                .background(Theme.colors.surfaceHigh, shape)
                .border(BorderStroke(borderWidth, borderColor), shape)
                .clip(shape)
                .clickable { onClick() },
            singleLine = singleLine,
            maxLines = if (singleLine) 1 else 11,
            textStyle = textStyle.copy(color = textColor),
            cursorBrush = SolidColor(Theme.colors.primary),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) {
                        HandleIcon(
                            leadingIcon = leadingIcon,
                            iconColor = iconColor,
                            singleLine = singleLine,
                            separatorColor = separatorColor
                        )
                    }

                    HandleInputText(
                        singleLine,
                        value,
                        placeholder,
                        textStyle,
                        innerTextField
                    )
                    if (trailingIcon != null) {
                        HandleIcon(
                            iconColor = iconColor,
                            singleLine = singleLine,
                            separatorColor = separatorColor,
                            trailingIcon = trailingIcon,
                            onClick = onClickTrailingIcon
                        )
                    }
                }
            }
        )

        AnimatedVisibility(
            visible = filteredSuggestions.isNotEmpty() && expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(shape)
                    .background(Theme.colors.surfaceHigh)
                    .border(BorderStroke(1.dp, Theme.colors.stroke), shape)
                    .heightIn(max = 300.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                filteredSuggestions.forEach { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSuggestionSelected(suggestion)
                                expanded = false
                            }
                            .padding(16.dp),
                        style = Theme.textStyle.body.medium
                    )
                }
            }
        }
    }
}

@Composable
private fun HandleIcon(
    leadingIcon: Int? = null,
    iconColor: Color,
    singleLine: Boolean,
    separatorColor: Color,
    trailingIcon: Int? = null,
    onClick: () -> Unit = { }
) {
    if (leadingIcon != null) {
        Image(
            painter = painterResource(id = leadingIcon),
            contentDescription = "Leading icon",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(iconColor),
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onClick)
        )
    }

    if (singleLine) {
        Spacer(Modifier.width(8.dp))
        VerticalDivider(
            color = separatorColor,
            modifier = Modifier
                .height(24.dp),
            thickness = 1.dp
        )
        Spacer(Modifier.width(16.dp))
    } else {
        Spacer(Modifier.width(8.dp))
    }

    if (trailingIcon != null) {
        Image(
            painter = painterResource(id = trailingIcon),
            contentDescription = "Trailing icon",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(iconColor),
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onClick)
        )
    }
}

@Composable
private fun RowScope.HandleInputText(
    singleLine: Boolean,
    value: String,
    placeholder: String,
    textStyle: TextStyle,
    innerTextField: @Composable () -> Unit,
) {
    val verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top

    Row(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight(),
        verticalAlignment = verticalAlignment
    ) {
        InputFieldContent(
            singleLine = singleLine,
            value = value,
            placeholder = placeholder,
            innerTextField = innerTextField,
            textStyle = textStyle
        )
    }
}

@Composable
private fun InputFieldContent(
    singleLine: Boolean,
    value: String,
    placeholder: String,
    textStyle: TextStyle,
    innerTextField: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (!singleLine) Spacer(modifier = Modifier.height(8.dp))
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = Theme.colors.text.hint,
                fontSize = 14.sp,
                style = textStyle
            )
        } else innerTextField()
    }
}

@PreviewLightDark
@Composable
fun PreviewTextField() {
    BasePreview {

        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var defaultText by remember { mutableStateOf("") }
            var showError by remember { mutableStateOf(false) }
            TextField(
                value = defaultText,
                onValueChange = {
                    defaultText = it
                    showError = true
                },
                placeholder = "Full name",
                enabled = true,
                leadingIcon = R.drawable.ic_outlined_user,
                showError = showError
            )


            var text by remember { mutableStateOf("") }
            var showError2 by remember { mutableStateOf(true) }
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    showError2 = true
                },
                placeholder = "Full name",
                enabled = true,
                leadingIcon = R.drawable.ic_outlined_user,
                showError = showError2,
                errorMessage = R.string.incorrect_password,
            )

            var defaultText2 by remember { mutableStateOf("") }
            var showError3 by remember { mutableStateOf(false) }
            TextField(
                value = defaultText2,
                onValueChange = {
                    defaultText2 = it
                    showError3 = true
                },
                placeholder = "label",
                enabled = false,
                trailingIcon = R.drawable.ic_filter_vertical,
                showError = showError3,
                onClick = {
                    showError3 = true
                }
            )

            var password by remember { mutableStateOf("") }
            var showPasswordError by remember { mutableStateOf(false) }
            var showPassword by remember { mutableStateOf(false) }
            TextField(
                value = password,
                onValueChange = {
                    password = it
                    showPasswordError = true
                },
                placeholder = "label",
                enabled = true,
                trailingIcon = if (showPassword) R.drawable.ic_eye_closed else R.drawable.ic_eye_opened,
                showError = showPasswordError,
                onClick = {
                    showPasswordError = true
                },
                onClickTrailingIcon = {
                    showPassword = !showPassword
                },
                errorMessage = R.string.incorrect_password,
                showText = showPassword
            )


            var multiLineText2 by remember { mutableStateOf("") }
            var showError4 by remember { mutableStateOf(false) }
            TextField(
                value = multiLineText2,
                onValueChange = { multiLineText2 = it },
                placeholder = "Description",
                singleLine = false,
                trailingIcon = null,
                showError = showError4,
            )

            val hints = listOf("Apple", "Banana", "Cherry", "Date", "Elderberry")
            var text4 by remember { mutableStateOf("") }
            TextField(
                value = text4,
                onValueChange = { text4 = it },
                placeholder = "Type fruit...",
                suggestions = hints,
                onSuggestionSelected = { text4 = it }
            )
        }

    }
}