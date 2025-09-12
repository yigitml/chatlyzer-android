package com.ch3x.chatlyzer.ui.screens.tutorial.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.ChangeAppLink
import com.ch3x.chatlyzer.ui.components.PrimaryButton

@Composable
fun TutorialActions(
    isLastStep: Boolean,
    onNextStep: () -> Unit,
    onNavigateAnalysis: () -> Unit,
    onNavigatePlatformSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TutorialActionButton(
            isLastStep = isLastStep,
            onNextStep = onNextStep,
            onNavigateAnalysis = onNavigateAnalysis
        )

        Spacer(modifier = Modifier.height(16.dp))

        ChangeAppLink(
            onClick = onNavigatePlatformSelection
        )
    }
}

@Composable
private fun TutorialActionButton(
    isLastStep: Boolean,
    onNextStep: () -> Unit,
    onNavigateAnalysis: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLastStep) {
        PrimaryButton(
            text = "Finish",
            onClick = onNavigateAnalysis,
            modifier = modifier
        )
    } else {
        PrimaryButton(
            text = "Next Step",
            onClick = onNextStep,
            modifier = modifier
        )
    }
} 