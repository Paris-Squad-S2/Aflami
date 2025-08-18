package com.paris_2.domain.game.entity

import com.paris_2.domain.game.utils.Genre

data class Answer(
    val questionId: String,
    val text: String,
    val genre: Genre,
    val isCorrect: Boolean
)