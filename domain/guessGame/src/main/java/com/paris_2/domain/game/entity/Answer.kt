package com.paris_2.domain.game.entity

import com.paris_2.domain.media.entity.Category


data class Answer(
    val questionId: String,
    val text: String,
    val genre: Category,
    val isCorrect: Boolean
)