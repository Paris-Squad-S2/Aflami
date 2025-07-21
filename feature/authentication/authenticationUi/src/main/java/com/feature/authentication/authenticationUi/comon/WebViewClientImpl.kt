package com.feature.authentication.authenticationUi.comon

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.MutableState

class WebViewClientImpl(
    private val isLoading: MutableState<Boolean>,
    private val hasError: MutableState<Boolean>,
) : WebViewClient() {
    override fun onPageStarted(
        view: WebView?,
        url: String?,
        favicon: Bitmap?
    ) {
        isLoading.value = true
        hasError.value = false
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        isLoading.value = false
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ){
        hasError.value = true
        isLoading.value = false
    }
}