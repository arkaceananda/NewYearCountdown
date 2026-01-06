package com.example.newyear.ui.theme

import android.graphics.Bitmap
import android.graphics.Canvas
import android.provider.MediaStore
import android.content.ContentValues
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newyear.logic.CountdownCalculator
import com.example.newyear.state.CountdownState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun CountdownScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    var state by remember { mutableStateOf(CountdownState()) }
    var targetTime by remember { mutableStateOf(CountdownCalculator.getTargetTime()) }
    var isInCelebrationMode by remember { mutableStateOf(false) }
    
    val view = LocalView.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(targetTime) {
        while (isActive && !isInCelebrationMode) {
            val newState = CountdownCalculator.calculateRemaining(targetTime)
            state = newState
            if (newState.isNewYear) {
                isInCelebrationMode = true
            }
            delay(1000L)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AnimatedContent(
            targetState = isInCelebrationMode,
            label = "ModeTransition",
            transitionSpec = {
                fadeIn(animationSpec = tween(1000)) togetherWith fadeOut(animationSpec = tween(500))
            }
        ) { inCelebration ->
            if (inCelebration) {
                NewYearCelebrationLayout(
                    onDismiss = {
                        targetTime = CountdownCalculator.getTargetTime()
                        isInCelebrationMode = false
                    },
                    onScreenshot = {
                        coroutineScope.launch {
                            try {
                                val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
                                val canvas = Canvas(bitmap)
                                view.draw(canvas)

                                val resolver = context.contentResolver
                                val contentValues = ContentValues().apply {
                                    put(MediaStore.Images.Media.DISPLAY_NAME, "new_year_celebration_${System.currentTimeMillis()}.png")
                                    put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                                    put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/NewYearApp")
                                }

                                val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                                imageUri?.let {
                                    resolver.openOutputStream(it)?.use { outputStream ->
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                                    }
                                    launch(Dispatchers.Main) {
                                        Toast.makeText(context, "Screenshot saved!", Toast.LENGTH_SHORT).show()
                                    }
                                } ?: throw Exception("MediaStore URI was null")

                            } catch (e: Exception) {
                                launch(Dispatchers.Main) {
                                    Toast.makeText(context, "Failed to save screenshot: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CountdownDisplay(state = state, targetYear = targetTime.year)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = state.currentDateLabel,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                }
            }
        }

        IconButton(
            onClick = onToggleTheme,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = if (isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                contentDescription = "Toggle Theme",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Composable
fun NewYearCelebrationLayout(
    onDismiss: () -> Unit,
    onScreenshot: () -> Unit
) {
    var showHintText by remember { mutableStateOf(false) }
    var showScreenshotButton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(4000)
        showHintText = true
        delay(500)
        showScreenshotButton = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onDismiss
            )
    ) {
        FireworksAnimation(modifier = Modifier.fillMaxSize())

        // Center the celebration text
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NewYearCelebrationText()
        }

        // "Tap anywhere" hint at the bottom
        AnimatedVisibility(
            visible = showHintText,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            enter = fadeIn()
        ) {
            Text(
                text = "Ketuk di mana saja untuk lanjut",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )
        }

        // Screenshot button
        AnimatedVisibility(
            visible = showScreenshotButton,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            IconButton(
                onClick = {
                    // By having its own clickable, it won't trigger the parent's onDismiss
                    onScreenshot()
                },
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = "Take Screenshot",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun NewYearCelebrationText() {
    val year = remember { java.time.LocalDateTime.now().year + 1 } // Assuming celebration is for the next year
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Selamat Tahun Baru",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "$year",
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun CountdownDisplay(state: CountdownState, targetYear: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Target: 1 January $targetYear",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier
                .glassmorphism()
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TimeBlock(value = state.days, label = "Days")
            TimeBlock(value = state.hours, label = "Hours")
            TimeBlock(value = state.minutes, label = "Mins")
            TimeBlock(value = state.seconds, label = "Secs")
        }
    }
}

@Composable
fun TimeBlock(value: Long, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString().padStart(2, '0'),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}
