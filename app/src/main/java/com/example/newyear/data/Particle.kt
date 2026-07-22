package com.example.newyear.data

import androidx.compose.ui.graphics.Color

data class Particle(
    var x: Float, var y: Float,
    var vx: Float, var vy: Float,
    var alpha: Float,
    val color: Color,
    val size: Float
)
