package com.feature.authentication.authenticationUi.comon

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

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
    val lastFailedUrl = remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    val retry: () -> Unit = {
        hasError.value = false
        isLoading.value = true
        reloadTrigger.intValue++
    }
    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    addJavascriptInterface(
                        object {
                            @Suppress("unused")
                            @JavascriptInterface
                            fun onMessageFromWeb(message: String) {
                                onWebMessageReceived(message)
                            }
                        },
                        "AndroidInterface"
                    )

                    webViewClient = object : WebViewClientImpl(
                        isLoading = isLoading,
                        hasError = hasError,
                        onNavigationEvent = onNavigationEvent
                    ) {
                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            super.onReceivedError(view, request, error)
                            if (request?.isForMainFrame == true) {
                                lastFailedUrl.value = request.url?.toString()
                            }
                        }
                    }
                    loadUrl(url)
                }
            },
            modifier = modifier.fillMaxSize(),
            update = { webView ->
                if (!hasError.value) {
                    val targetUrl = lastFailedUrl.value ?: url
                    webView.loadUrl(targetUrl)
                }
            },
        )

        when {
            isLoading.value -> loadingPlaceholder()
            hasError.value -> errorPlaceholder(retry)
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            webView.destroy()
        }
    }

}