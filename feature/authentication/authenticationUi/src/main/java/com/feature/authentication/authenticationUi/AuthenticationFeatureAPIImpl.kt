package com.feature.authentication.authenticationUi

import android.content.Intent
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import kotlin.jvm.java

class AuthenticationFeatureAPIImpl(
    private val context: android.content.Context
) : AuthenticationFeatureAPI {
    override fun invoke() {
        val intent = Intent(context, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}