package com.example.newyear.state

data class CountdownState(
    val days: Long = 0,
    val hours: Long = 0,
    val minutes: Long = 0,
    val seconds: Long = 0,
    val isNewYear: Boolean = false,
    val currentDateLabel: String = ""
)
