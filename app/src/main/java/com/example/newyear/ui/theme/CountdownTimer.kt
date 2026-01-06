package com.example.newyear.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newyear.state.CountdownState

@Composable
fun CountdownDisplay(
    state: CountdownState,
    isDarkTheme: Boolean
) {
    val glassColor = if (isDarkTheme) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.05f)
    val textColor = MaterialTheme.colorScheme.onBackground

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "COUNTDOWN TO NEW YEAR",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 2.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(glassColor)
                .padding(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Days (Only if > 0)
            if (state.days > 0) {
                TimeUnit(state.days, "DAYS", textColor)
                Separator(textColor)
            }
            TimeUnit(state.hours, "HOURS", textColor)
            Separator(textColor)
            TimeUnit(state.minutes, "MINUTES", textColor)
            Separator(textColor)
            TimeUnit(state.seconds, "SECONDS", textColor, isBold = true)
        }
    }
}

@Composable
private fun TimeUnit(value: Long, label: String, color: Color, isBold: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString().padStart(2, '0'),
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 48.sp,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Medium,
                fontFeatureSettings = "tnum" // Tabular numbers agar lebar angka tetap
            ),
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color.copy(alpha = 0.5f),
            fontSize = 10.sp
        )
    }
}

@Composable
private fun Separator(color: Color) {
    Text(
        text = ":",
        fontSize = 32.sp,
        color = color.copy(alpha = 0.3f),
        modifier = Modifier.padding(horizontal = 8.dp).offset(y = (-10).dp)
    )
}