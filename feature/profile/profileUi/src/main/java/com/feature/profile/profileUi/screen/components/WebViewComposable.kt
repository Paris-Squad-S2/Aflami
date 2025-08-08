package com.feature.profile.profileUi.screen.components

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.feature.profile.profileUi.utils.WebViewClientImpl

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewComposable(
    modifier: Modifier = Modifier,
    url: String,
    onWebMessageReceived: (String) -> Unit,
    loadingPlaceholder: @Composable () -> Unit,
    errorPlaceholder: @Composable (onRetry: () -> Unit) -> Unit,
    onNavigationEvent: (() -> Unit)? = null,
) {
    val isLoading = remember { mutableStateOf(true) }
    val hasError = remember { mutableStateOf(false) }
    val reloadTrigger = remember { mutableIntStateOf(0) }

    val retry: () -> Unit = {
        hasError.value = false
        isLoading.value = true
        reloadTrigger.intValue++
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true

                addJavascriptInterface(
                    object {
                        @JavascriptInterface
                        fun onMessageFromWeb(message: String) {
                            onWebMessageReceived(message)
                        }
                    },
                    "AndroidInterface"
                )

                webViewClient = WebViewClientImpl(isLoading, hasError, onNavigationEvent)

                loadUrl(url)
            }
        },
        modifier = modifier.fillMaxSize(),
        update = { webView ->
            if (!hasError.value) {
                webView.loadUrl(url)
            }
        }
    )

    when {
        isLoading.value -> loadingPlaceholder()
        hasError.value -> errorPlaceholder(retry)
    }
}