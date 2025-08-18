package com.feature.profile.profileUi.navigation

import android.content.Context
import android.content.Intent
import com.feature.profile.profileUi.ProfileDestinationActivity

fun navigateDestination(
    context: Context,
    destination: Destination
) {
    val intent = Intent(context, ProfileDestinationActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.putExtra("destination", destination.toJson())
    context.startActivity(intent)
}