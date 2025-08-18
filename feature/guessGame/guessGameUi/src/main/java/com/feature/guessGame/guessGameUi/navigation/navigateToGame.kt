package com.feature.guessGame.guessGameUi.navigation

import android.content.Context
import android.content.Intent
import com.feature.guessGame.guessGameUi.GameActivity

fun navigateToGame(
    context: Context,
    gameDestination: Destination
) {
    val intent = Intent(context, GameActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.putExtra("gameDestination", gameDestination.toJson())
    context.startActivity(intent)
}