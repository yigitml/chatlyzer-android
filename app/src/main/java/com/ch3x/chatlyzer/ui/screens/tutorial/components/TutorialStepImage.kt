package com.ch3x.chatlyzer.ui.screens.tutorial.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun TutorialStepImage(
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = modifier.fillMaxWidth()
    )
} 