package com.ch3x.chatlyzer.ui.screens.introduction.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch3x.chatlyzer.R

@Composable
fun StatsSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.left_laurel),
                contentDescription = "Left Laurel",
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(3f)
            ) {
                Text(
                    text = "100,000+",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = "Chats Analyzed",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Image(
                painter = painterResource(id = R.drawable.left_laurel),
                contentDescription = "Right Laurel",
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .graphicsLayer {
                        rotationY = 180f
                    },
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }
    }
} 