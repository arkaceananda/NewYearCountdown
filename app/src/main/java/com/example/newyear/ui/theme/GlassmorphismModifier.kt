package com.example.newyear.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.glassmorphism(
    shape: Shape = RoundedCornerShape(32.dp),
    color: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
    borderWidth: Dp = 1.dp,
    borderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
): Modifier = this
    .clip(shape)
    .background(color)
    .border(borderWidth, borderColor, shape)
