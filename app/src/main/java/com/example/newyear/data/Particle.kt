package com.example.newyear.data

import android.graphics.Color

data class Particle(
    var x: Float, var y: Float,
    var vx: Float, var vy: Float,
    var alpha: Float,
    val color: androidx.compose.ui.graphics.Color,
    val size: Float
)
