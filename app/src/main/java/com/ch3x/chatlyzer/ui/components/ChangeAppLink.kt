package com.ch3x.chatlyzer.ui.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ChangeAppLink(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        content = {
            Text(
                "Change Platform", fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
        },
        modifier = modifier,
        onClick = onClick,
    )
}