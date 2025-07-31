package com.paris_2.aflami.bottomNavBar

import android.content.Context
import android.content.Intent

class AppNavigationAPIImpl(
    private val context: Context
) : AppNavigationAPI {
    override fun invoke() {
        val intent = Intent(context, AppNavigation::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}