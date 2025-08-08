package com.paris_2.domain.game.models

import kotlinx.datetime.LocalDateTime

data class GameSession(
    val id: String,
    val level: GameLevel,
    val currentQuestion: Question,
    val score: Int,
    val isCompleted: Boolean,
    val startTime: LocalDateTime
) {
    enum class GameLevel {
        EASY,
        MEDIUM,
        HARD
    }
}