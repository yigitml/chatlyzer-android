package com.ch3x.chatlyzer.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

val ButtonShape = RoundedCornerShape(16.dp)
val CardShape = RoundedCornerShape(24.dp)
val InputShape = RoundedCornerShape(16.dp)
val PillShape = RoundedCornerShape(50) // Percent for full pill