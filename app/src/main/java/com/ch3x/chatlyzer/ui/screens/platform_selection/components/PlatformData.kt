package com.ch3x.chatlyzer.ui.screens.platform_selection.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.R
import com.ch3x.chatlyzer.ui.screens.tutorial.components.Platform
import com.ch3x.chatlyzer.ui.theme.InstagramGradientEnd
import com.ch3x.chatlyzer.ui.theme.InstagramGradientStart
import com.ch3x.chatlyzer.ui.theme.WhatsAppGreen

data class PlatformData(
    val platform: Platform,
    val name: String,
    val iconRes: Int,
    val backgroundGradient: Brush
) {
    @Composable
    fun IconContent() {
        Image(
            painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(18.dp)
                .shadow(elevation = 4.dp)
        )
    }

    companion object {
        fun getPlatforms() = listOf(
            PlatformData(
                platform = Platform.WHATSAPP,
                name = "Whatsapp",
                iconRes = R.drawable.whatsapp_logo,
                backgroundGradient = Brush.linearGradient(
                    listOf(WhatsAppGreen, Color(0xFF2E7D32))
                )
            ),
            PlatformData(
                platform = Platform.INSTAGRAM,
                name = "Instagram",
                iconRes = R.drawable.instagram_logo,
                backgroundGradient = Brush.linearGradient(
                    colors = listOf(
                        InstagramGradientStart,
                        InstagramGradientEnd
                    )
                )
            )
        )
    }
} 