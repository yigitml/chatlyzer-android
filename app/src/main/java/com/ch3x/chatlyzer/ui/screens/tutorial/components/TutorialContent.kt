package com.ch3x.chatlyzer.ui.screens.tutorial.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun TutorialContent(
    selectedPlatform: Platform,
    onNavigateAnalysis: () -> Unit,
    onNavigatePlatformSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stepState = remember { mutableIntStateOf(1) }
    val stepIndex = stepState.intValue
    
    val steps = TutorialDataProvider.getTutorialSteps()[selectedPlatform] ?: emptyList()
    val currentStep = steps.getOrNull(stepIndex - 1)
    val isLastStep = stepIndex == steps.size

    if (currentStep == null) {
        UnsupportedPlatform()
    } else {
        TutorialStepDisplay(
            step = currentStep,
            isLastStep = isLastStep,
            onNextStep = { stepState.intValue++ },
            onNavigateAnalysis = onNavigateAnalysis,
            onNavigatePlatformSelection = onNavigatePlatformSelection,
            modifier = modifier
        )
    }
} 