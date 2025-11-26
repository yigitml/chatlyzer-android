package com.ch3x.chatlyzer.ui.components.animations

import androidx.compose.animation.core.*
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import kotlin.math.roundToInt

/**
 * Text that counts up from 0 to target value
 */
@Composable
fun CountUpText(
    targetValue: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    durationMillis: Int = 800,
    delayMillis: Int = 0,
    prefix: String = "",
    suffix: String = ""
) {
    var currentValue by remember { mutableStateOf(0) }
    var hasAnimated by remember { mutableStateOf(false) }

    LaunchedEffect(targetValue) {
        if (!hasAnimated) {
            kotlinx.coroutines.delay(delayMillis.toLong())
            hasAnimated = true
            
            val startTime = System.currentTimeMillis()
            val endTime = startTime + durationMillis
            
            while (System.currentTimeMillis() < endTime) {
                val progress = (System.currentTimeMillis() - startTime).toFloat() / durationMillis
                val easedProgress = FastOutSlowInEasing.transform(progress)
                currentValue = (targetValue * easedProgress).roundToInt()
                kotlinx.coroutines.delay(16) // ~60fps
            }
            currentValue = targetValue
        }
    }

    Text(
        text = "$prefix$currentValue$suffix",
        modifier = modifier,
        style = style,
        color = color,
        fontWeight = fontWeight
    )
}

/**
 * Float version for decimal numbers
 */
@Composable
fun CountUpFloatText(
    targetValue: Float,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    durationMillis: Int = 800,
    delayMillis: Int = 0,
    decimalPlaces: Int = 1,
    prefix: String = "",
    suffix: String = ""
) {
    var currentValue by remember { mutableStateOf(0f) }
    var hasAnimated by remember { mutableStateOf(false) }

    LaunchedEffect(targetValue) {
        if (!hasAnimated) {
            kotlinx.coroutines.delay(delayMillis.toLong())
            hasAnimated = true
            
            val startTime = System.currentTimeMillis()
            val endTime = startTime + durationMillis
            
            while (System.currentTimeMillis() < endTime) {
                val progress = (System.currentTimeMillis() - startTime).toFloat() / durationMillis
                val easedProgress = FastOutSlowInEasing.transform(progress)
                currentValue = targetValue * easedProgress
                kotlinx.coroutines.delay(16) // ~60fps
            }
            currentValue = targetValue
        }
    }

    val formattedValue = String.format("%.${decimalPlaces}f", currentValue)
    
    Text(
        text = "$prefix$formattedValue$suffix",
        modifier = modifier,
        style = style,
        color = color,
        fontWeight = fontWeight
    )
}

/**
 * Percentage counter with animated progress
 */
@Composable
fun CountUpPercentage(
    targetPercentage: Float,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    durationMillis: Int = 800,
    delayMillis: Int = 0
) {
    CountUpFloatText(
        targetValue = targetPercentage,
        modifier = modifier,
        style = style,
        color = color,
        fontWeight = fontWeight,
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        decimalPlaces = 0,
        suffix = "%"
    )
}
