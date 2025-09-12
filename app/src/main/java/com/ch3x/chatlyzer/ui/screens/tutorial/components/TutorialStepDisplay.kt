package com.ch3x.chatlyzer.ui.screens.tutorial.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TutorialStepDisplay(
    step: TutorialStep,
    isLastStep: Boolean,
    onNextStep: () -> Unit,
    onNavigateAnalysis: () -> Unit,
    onNavigatePlatformSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        TutorialStepHeader(stepName = step.name)

        Spacer(modifier = Modifier.height(48.dp))

        TutorialStepImage(imageRes = step.imageRes)

        Spacer(modifier = Modifier.weight(1f))

        TutorialActions(
            isLastStep = isLastStep,
            onNextStep = onNextStep,
            onNavigateAnalysis = onNavigateAnalysis,
            onNavigatePlatformSelection = onNavigatePlatformSelection
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
} 