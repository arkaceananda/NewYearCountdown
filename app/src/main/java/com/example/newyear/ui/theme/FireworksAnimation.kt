package com.example.newyear.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.isActive
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// A single particle in our firework explosion
private data class Particle(
    var x: Float, var y: Float, // Position
    var vx: Float, var vy: Float, // Velocity
    var alpha: Float, // Transparency
    val color: Color, // Color
    val size: Float // Size
)

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

    // The animation engine. It runs in a side-effect and updates the particle state.
    LaunchedEffect(canvasSize) {
        if (canvasSize == IntSize.Zero) return@LaunchedEffect

        var lastFrameTime = 0L
        while (isActive) {
            // Wait for the next frame.
            withFrameNanos { frameTime ->
                if (lastFrameTime == 0L) {
                    lastFrameTime = frameTime
                }
                // Delta time ensures animation is smooth regardless of frame rate.
                val delta = (frameTime - lastFrameTime) / 1_000_000_000f
                lastFrameTime = frameTime

                // 1. Spawn new fireworks randomly
                if (random.nextFloat() < 0.05f) {
                    val cx = random.nextFloat() * canvasSize.width
                    val cy = random.nextFloat() * canvasSize.height * 0.7f
                    val baseColor = colors.random()

                    repeat(100) { // 100 particles per explosion
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

                // 2. Update and remove dead particles
                val iterator = particles.iterator()
                while (iterator.hasNext()) {
                    val p = iterator.next()
                    p.x += p.vx * delta
                    p.y += p.vy * delta
                    p.vy += 300f * delta // Apply gravity
                    p.alpha -= 0.8f * delta // Fade out

                    if (p.alpha <= 0f) {
                        iterator.remove() // Remove particle when it's no longer visible
                    }
                }
            }
        }
    }

    // The Canvas is now only responsible for drawing the current state of particles.
    Canvas(modifier = modifier
        .fillMaxSize()
        .onSizeChanged { newSize ->
            canvasSize = newSize // Capture the canvas size to use in our animation logic
        }) {
        // Draw each particle
        particles.forEach { p ->
            drawCircle(
                color = p.color.copy(alpha = p.alpha.coerceIn(0f, 1f)),
                radius = p.size,
                center = Offset(p.x, p.y)
            )
        }
    }
}