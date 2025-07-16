package com.repository.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getCurrentDate(): LocalDateTime {
    return Clock.System
        .now()
        .toLocalDateTime(TimeZone.Companion.currentSystemDefault())
}