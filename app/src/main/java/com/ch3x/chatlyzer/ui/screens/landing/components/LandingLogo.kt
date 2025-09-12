package com.ch3x.chatlyzer.ui.screens.landing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.LogoComponent

@Composable
fun LandingLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(144.dp)
            .background(MaterialTheme.colorScheme.background)
            .border(2.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(144.dp)),
        contentAlignment = Alignment.Center
    ) {
        LogoComponent(size = 44)
    }
} 