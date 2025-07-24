package com.feature.search.searchUi

import android.content.Intent
import com.feature.search.searchApi.SearchFeatureAPI
import kotlin.jvm.java

class SearchFeatureAPIImpl(
    private val context: android.content.Context,
) : SearchFeatureAPI {
    override fun invoke() {
        val intent = Intent(context, SearchActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}