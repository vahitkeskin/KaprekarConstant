package com.vahitkeskin.kaprekar.presentation.ui.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

/**
 * 3D visual styling and two-finger gesture manipulation card for mathematical formulas and outputs.
 * Features:
 * - Two-finger multitouch pan, pinch-to-zoom and 3D rotation/tilt.
 * - Dynamic lighting sheen gradient reacting to tilt angle.
 * - Double-tap or reset button to smoothly animate back to original position.
 * - 3D depth shadow and layered borders.
 */
@Composable
fun Interactive3DCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    accentColor: Color = BrandPink,
    elevation: Dp = 8.dp,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
    badgeTitle: String? = "3D",
    enableDoubleTapReset: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    // Animatable transformations
    val scale = remember { Animatable(1f) }
    val rotationX = remember { Animatable(0f) }
    val rotationY = remember { Animatable(0f) }
    val rotationZ = remember { Animatable(0f) }
    val translationX = remember { Animatable(0f) }
    val translationY = remember { Animatable(0f) }

    val isTransformed by remember {
        derivedStateOf {
            scale.value != 1f ||
            rotationX.value != 0f ||
            rotationY.value != 0f ||
            rotationZ.value != 0f ||
            translationX.value != 0f ||
            translationY.value != 0f
        }
    }

    fun resetCard() {
        coroutineScope.launch {
            val animSpec = spring<Float>(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
            launch { scale.animateTo(1f, animSpec) }
            launch { rotationX.animateTo(0f, animSpec) }
            launch { rotationY.animateTo(0f, animSpec) }
            launch { rotationZ.animateTo(0f, animSpec) }
            launch { translationX.animateTo(0f, animSpec) }
            launch { translationY.animateTo(0f, animSpec) }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                this.scaleX = scale.value
                this.scaleY = scale.value
                this.rotationX = rotationX.value
                this.rotationY = rotationY.value
                this.rotationZ = rotationZ.value
                this.translationX = translationX.value
                this.translationY = translationY.value
                this.cameraDistance = 14f * density
                this.shadowElevation = if (isTransformed) (elevation.value * 2f) * density else elevation.value * density
            }
            .pointerInput(Unit) {
                if (enableDoubleTapReset) {
                    detectTapGestures(
                        onDoubleTap = {
                            resetCard()
                        }
                    )
                }
            }
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    coroutineScope.launch {
                        // Zoom limits
                        val newScale = (scale.value * zoom).coerceIn(0.6f, 2.5f)
                        scale.snapTo(newScale)

                        // 2-Finger pan & tilt translation
                        translationX.snapTo(translationX.value + pan.x)
                        translationY.snapTo(translationY.value + pan.y)

                        // 3D tilt calculated from pan movement with dampening
                        val targetRotY = (rotationY.value + (pan.x * 0.15f)).coerceIn(-35f, 35f)
                        val targetRotX = (rotationX.value - (pan.y * 0.15f)).coerceIn(-35f, 35f)
                        rotationY.snapTo(targetRotY)
                        rotationX.snapTo(targetRotX)

                        // Z rotation from two-finger twist
                        rotationZ.snapTo((rotationZ.value + (rotation * 0.75f)).coerceIn(-45f, 45f))
                    }
                }
            }
            .shadow(
                elevation = if (isTransformed) elevation * 1.8f else elevation,
                shape = shape,
                ambientColor = accentColor.copy(alpha = 0.25f),
                spotColor = accentColor.copy(alpha = 0.45f)
            )
            .clip(shape)
            .background(containerColor)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        accentColor.copy(alpha = 0.12f + (rotationX.value.coerceIn(-30f, 30f) / 180f)),
                        Color.White.copy(alpha = 0.04f),
                        BrandCyan.copy(alpha = 0.08f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
    ) {
        // 3D Metallic Edge Border
        Surface(
            shape = shape,
            color = Color.Transparent,
            border = BorderStroke(
                width = if (isTransformed) 1.8.dp else 1.2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        accentColor.copy(alpha = if (isTransformed) 0.9f else 0.55f),
                        BrandCyan.copy(alpha = if (isTransformed) 0.8f else 0.4f),
                        accentColor.copy(alpha = 0.3f)
                    )
                )
            ),
            modifier = Modifier.matchParentSize()
        ) {}

        // Content Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            content()
        }

        // Top-Right 3D Interactive Badge / Reset Control
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (isTransformed) {
                Surface(
                    onClick = { resetCard() },
                    shape = RoundedCornerShape(10.dp),
                    color = BrandPink.copy(alpha = 0.9f),
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset 3D",
                            tint = Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "Reset",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else if (badgeTitle != null) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = accentColor.copy(alpha = 0.18f),
                    border = BorderStroke(0.8.dp, accentColor.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ViewInAr,
                            contentDescription = "3D Interactive",
                            tint = accentColor,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = badgeTitle,
                            color = accentColor,
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Interactive3DCardPreview() {
    ThemePreview {
        Interactive3DCard(
            accentColor = BrandPink,
            badgeTitle = "3D DEMO"
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Interactive 3D Card", fontWeight = FontWeight.Bold)
                Text("Two-finger gestures: Zoom, Tilt & Pan", fontSize = 12.sp)
            }
        }
    }
}
