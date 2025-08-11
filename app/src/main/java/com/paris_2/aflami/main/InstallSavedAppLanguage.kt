package com.paris_2.aflami.main

import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

@Composable
fun InstallSavedAppLanguage(
    context: Context,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        mainViewModel.getLastSelectedAppLanguage().collectLatest { language ->
            withContext(Dispatchers.Main) {
                changeAppLanguage(
                    context = context,
                    language
                )
            }
        }
    }
}

private fun changeAppLanguage(context: Context, languageCode: String) {
    val localeListCompat = LocaleListCompat.forLanguageTags(languageCode)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val localeManager = context.getSystemService(android.app.LocaleManager::class.java)
        localeManager?.applicationLocales = LocaleList.forLanguageTags(languageCode)
    } else {
        AppCompatDelegate.setApplicationLocales(localeListCompat)
    }
}
