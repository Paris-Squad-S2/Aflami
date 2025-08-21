package com.paris.domain.game.entity

import com.paris.domain.media.entity.Category


data class Answer(
    val questionId: String,
    val text: String,
    val genre: Category,
    val isCorrect: Boolean
)