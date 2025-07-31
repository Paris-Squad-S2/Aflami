package com.feature.search.searchUi

import android.content.Context
import android.content.Intent
import com.feature.search.searchApi.SearchFeatureAPI
import kotlin.jvm.java

class SearchFeatureAPIImpl(
    private val context: Context,
) : SearchFeatureAPI {
    override fun invoke() {
        val intent = Intent(context, SearchActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}