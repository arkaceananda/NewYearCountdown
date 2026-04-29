package com.example.newyear.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.example.newyear.data.Particle
import kotlinx.coroutines.isActive
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


@Composable
fun FireworksAnimation(
    modifier: Modifier = Modifier
) {
    val particles = remember { mutableStateListOf<Particle>() }
    val random = remember { Random(System.currentTimeMillis()) }
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    val colors = listOf(
        Color(0xFFFF3B30), Color(0xFF007AFF), Color(0xFF34C759),
        Color(0xFFFFCC00), Color(0xFFAF52DE), Color.White
    )

    LaunchedEffect(canvasSize) {
        if (canvasSize == IntSize.Zero) return@LaunchedEffect

        var lastFrameTime = 0L
        while (isActive) {
            withFrameNanos { frameTime ->
                if (lastFrameTime == 0L) {
                    lastFrameTime = frameTime
                }
                val delta = (frameTime - lastFrameTime) / 1_000_000_000f
                lastFrameTime = frameTime

                if (random.nextFloat() < 0.05f) {
                    val cx = random.nextFloat() * canvasSize.width
                    val cy = random.nextFloat() * canvasSize.height * 0.7f
                    val baseColor = colors.random()

                    repeat(100) {
                        val angle = random.nextFloat() * 2 * PI.toFloat()
                        val speed = random.nextFloat() * 400f + 100f
                        particles.add(
                            Particle(
                                x = cx, y = cy,
                                vx = (cos(angle) * speed),
                                vy = (sin(angle) * speed),
                                alpha = 1f,
                                color = baseColor,
                                size = random.nextFloat() * 5f + 2f
                            )
                        )
                    }
                }

                val iterator = particles.iterator()
                while (iterator.hasNext()) {
                    val p = iterator.next()
                    p.x += p.vx * delta
                    p.y += p.vy * delta
                    p.vy += 300f * delta
                    p.alpha -= 0.8f * delta

                    if (p.alpha <= 0f) {
                        iterator.remove()
                    }
                }
            }
        }
    }

    Canvas(modifier = modifier
        .fillMaxSize()
        .onSizeChanged { newSize ->
            canvasSize = newSize
        }) {
        particles.forEach { p ->
            drawCircle(
                color = p.color.copy(alpha = p.alpha.coerceIn(0f, 1f)),
                radius = p.size,
                center = Offset(p.x, p.y)
            )
        }
    }
}