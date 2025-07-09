package com.feature.search.searchUi.screen.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.feature.search.searchUi.R
import com.feature.search.searchUi.screen.search.SearchScreenInteractionListener
import com.paris_2.aflami.designsystem.components.AflamiButton
import com.paris_2.aflami.designsystem.components.AflamiDialog
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType

@Composable
fun FilterDialog(
    searchScreenInteractionListener: SearchScreenInteractionListener,
) {
    AflamiDialog(
        onDismiss = searchScreenInteractionListener::onFilterButtonClick,
        title = R.string.filter_result,
    ) {
        Column {
            AflamiButton(
                text = R.string.apply,
                onClick = { },
                type = ButtonType.Primary,
                state = ButtonState.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            AflamiButton(
                text = R.string.clear,
                onClick = searchScreenInteractionListener::onFilterButtonClick,
                type = ButtonType.Secondary,
                state = ButtonState.Normal,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}