package com.paris_2.aflami.bottomNavBar

import android.content.Context
import android.content.Intent
import com.paris_2.aflami.bottomNavBar.ui.BottomNavBarActivity

class BottomNavBarAPIImpl(
    private val context: Context
) : BottomNavBarAPI {
    override fun invoke() {
        val intent = Intent(context, BottomNavBarActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}