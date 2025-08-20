package com.feature.guessGame.guessGameUi

import com.paris_2.domain.game.entity.Question

 sealed class HintUsageResult {
    data class Success(val updatedQuestion: Question?) : HintUsageResult()
    object AlreadyUsed : HintUsageResult()
    object NotEnoughPoints : HintUsageResult()
    data class Failed(val error: String) : HintUsageResult()
}

