package com.ch3x.chatlyzer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.theme.ButtonShape
import com.ch3x.chatlyzer.ui.theme.PrimaryGradientColors

import com.ch3x.chatlyzer.ui.components.animations.BouncyButton

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    gradient: Brush = Brush.horizontalGradient(PrimaryGradientColors)
) {
    BouncyButton(
        onClick = onClick,
        enabled = enabled,
        bounciness = 0.94f
    ) {
        Box(
            modifier = modifier
                .height(56.dp)
                .fillMaxWidth()
                .clip(ButtonShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = if (enabled) gradient else Brush.linearGradient(
                            listOf(Color.Gray, Color.Gray)
                        )
                    )
            )

            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
fun GradientButtonPreview() {
    GradientButton(text = "Get Started", onClick = {})
}
