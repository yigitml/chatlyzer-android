package com.ch3x.chatlyzer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.theme.CardShape
import com.ch3x.chatlyzer.ui.theme.SurfaceGlass

/**
 * Enhanced glass card with backdrop blur effect
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    blurRadius: Dp = 12.dp,
    glassOpacity: Float = 0.12f,
    borderOpacity: Float = 0.15f,
    content: @Composable ColumnScope.() -> Unit
) {
    val isDarkTheme = androidx.compose.foundation.isSystemInDarkTheme()
    
    val containerColor = if (isDarkTheme) {
        MaterialTheme.colorScheme.onBackground.copy(alpha = glassOpacity)
    } else {
        androidx.compose.material3.MaterialTheme.colorScheme.surface
    }

    val contentColor = if (isDarkTheme) {
        MaterialTheme.colorScheme.onBackground
    } else {
        androidx.compose.material3.MaterialTheme.colorScheme.onSurface
    }

    val borderBrush = if (isDarkTheme) {
        Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.onBackground.copy(alpha = borderOpacity * 1.5f),
                MaterialTheme.colorScheme.onBackground.copy(alpha = borderOpacity * 0.5f)
            )
        )
    } else {
        // Subtle border for light mode or transparent
        Brush.verticalGradient(
            colors = listOf(
                androidx.compose.material3.MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                androidx.compose.material3.MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.1f)
            )
        )
    }

    val shadowColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                // Only draw shadow/blur effect in dark mode or if specifically desired
                if (isDarkTheme) {
                    drawIntoCanvas { canvas ->
                        val paint = Paint()
                        paint.color = shadowColor
                        val frameworkPaint = paint.asFrameworkPaint()
                        frameworkPaint.setShadowLayer(
                            8.dp.toPx(),
                            0f,
                            2.dp.toPx(),
                            Color.Black.copy(alpha = 0.1f).toArgb()
                        )
                    }
                }
            },
        shape = CardShape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = BorderStroke(
            width = 1.dp,
            brush = borderBrush
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isDarkTheme) 0.dp else 2.dp // Use standard elevation in light mode
        ),
        content = {
            Column(
                modifier = Modifier.padding(16.dp),
                content = content
            )
        }
    )
}
