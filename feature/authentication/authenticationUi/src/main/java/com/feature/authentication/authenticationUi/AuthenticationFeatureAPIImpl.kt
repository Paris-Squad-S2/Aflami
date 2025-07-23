package com.feature.authentication.authenticationUi

import android.content.Intent
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import kotlin.jvm.java

class AuthenticationFeatureAPIImpl(
    private val context: android.content.Context
) : AuthenticationFeatureAPI {
    override fun invoke() {
        val intent = Intent(context, AuthActivity::class.java)
        context.startActivity(intent)
    }
}