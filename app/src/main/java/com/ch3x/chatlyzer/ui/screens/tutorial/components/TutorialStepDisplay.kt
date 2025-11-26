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

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun TutorialStepDisplay(
    step: TutorialStep,
    isLastStep: Boolean,
    onNextStep: () -> Unit,
    onNavigateAnalysis: () -> Unit,
    onNavigatePlatformSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val screenHeight = maxHeight
        val isCompact = screenHeight < 600.dp
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (isCompact) Arrangement.Top else Arrangement.SpaceBetween
        ) {
            val topSpacerHeight = if (isCompact) 16.dp else 32.dp
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(topSpacerHeight))
                TutorialStepHeader(stepName = step.name)
                Spacer(modifier = Modifier.height(if (isCompact) 24.dp else 48.dp))
                TutorialStepImage(imageRes = step.imageRes)
            }

            if (!isCompact) {
                Spacer(modifier = Modifier.weight(1f))
            } else {
                Spacer(modifier = Modifier.height(32.dp))
            }

            TutorialActions(
                isLastStep = isLastStep,
                onNextStep = onNextStep,
                onNavigateAnalysis = onNavigateAnalysis,
                onNavigatePlatformSelection = onNavigatePlatformSelection
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
} 