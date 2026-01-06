package com.example.newyear.logic

import com.example.newyear.state.CountdownState
import java.time.Duration
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.Locale

object CountdownCalculator {

    fun getTargetTime(): LocalDateTime {
        // ganti untuk mengetes
        // return LocalDateTime.now().plusSeconds(15)
        val now = LocalDateTime.now()
        val currentYear = now.year

        val thisYearTarget = LocalDateTime.of(currentYear, Month.JANUARY, 1, 0, 0, 0)

        return if (now.isBefore(thisYearTarget)) {
            thisYearTarget
        } else {
            thisYearTarget.plusYears(1)
        }
    }

    fun getCurrentDisplay (): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy", Locale.ENGLISH)
        return now.format(formatter)
    }

    fun calculateRemaining (targetTime: LocalDateTime): CountdownState {
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
            currentDateLabel = getCurrentDisplay()
        )
    }
}