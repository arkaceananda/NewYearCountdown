package com.example.newyear.logic

import com.example.newyear.data.CountdownState
import java.time.Duration
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Extension function to calculate the next January 1st from the current [LocalDateTime].
 */
fun LocalDateTime.getNextNewYear(): LocalDateTime {
    val targetYear = LocalDateTime.of(this.year, Month.JANUARY, 1, 0, 0, 0)

    return if (this.isBefore(targetYear)) {
        targetYear
    } else {
        targetYear.plusYears(1)
    }
}

/**
 * Extension function to format [LocalDateTime] to a displayable string.
 */
fun LocalDateTime.formatToDisplay(): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy", Locale.ENGLISH)
    return this.format(formatter)
}

/**
 * Calculates the remaining time until [targetTime].
 */
fun calculateRemaining(targetTime: LocalDateTime): CountdownState {
    val now = LocalDateTime.now()
    val duration = Duration.between(now, targetTime)

    if (duration.isNegative || duration.isZero) {
        return CountdownState(isNewYear = true)
    }

    return CountdownState(
        days = duration.toDays(),
        hours = duration.toHours() % 24,
        minutes = duration.toMinutes() % 60,
        seconds = duration.toSeconds() % 60,
        isNewYear = false,
        currentDateLabel = now.formatToDisplay()
    )
}
