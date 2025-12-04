package com.ch3x.chatlyzer.ui.screens.analysis_carousel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ch3x.chatlyzer.ui.components.GlassCard
import com.ch3x.chatlyzer.ui.components.LoadingState
import com.ch3x.chatlyzer.ui.components.analysis_ui_builder.AnalysisType
import com.ch3x.chatlyzer.ui.components.analysis_ui_builder.AnalysisUIBuilder
import com.ch3x.chatlyzer.util.JsonMapFactory
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnalysisCarouselScreen(
    chatId: String,
    onNavigateToCreate: () -> Unit,
    viewModel: AnalysisCarouselViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(chatId) {
        viewModel.loadData(chatId)
    }

    if (state.isLoading) {
        LoadingState()
        return
    }

    if (state.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = state.error ?: "Unknown error", color = MaterialTheme.colorScheme.error)
        }
        return
    }

    val analysisTypes = AnalysisType.values()
    
    if (analysisTypes.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "No analysis types available",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                com.ch3x.chatlyzer.ui.components.GradientButton(
                    text = "Start Analysis",
                    onClick = onNavigateToCreate
                )
            }
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Ghost Mode Warning Banner
        if (state.isGhostMode) {
            GlassCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "👻",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Column {
                        Text(
                            text = "Ghost Mode Active",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "This data is temporary and will disappear when you navigate away.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

    val initialPage = Int.MAX_VALUE / 2
    // Adjust initial page to start at the first item (index 0)
    val startIndex = initialPage - (initialPage % analysisTypes.size)
    
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { Int.MAX_VALUE }
    )

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp),
        pageSpacing = 16.dp,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val actualPage = (page % analysisTypes.size)
        val type = analysisTypes[actualPage]
        val analysis = state.analysesByType[type]

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset = (
                        (pagerState.currentPage - page) + pagerState
                            .currentPageOffsetFraction
                    ).absoluteValue

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )

                    scaleY = lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
             Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp) 
            ) {
                if (analysis != null && analysis.result != null) {
                     AnalysisUIBuilder().BuildAnalysisUI(
                        analysisType = type,
                        resultDataJson = JsonMapFactory.toJson(analysis.result)
                    )
                } else {
                    // Placeholder for missing analysis
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                         Column(horizontalAlignment = Alignment.CenterHorizontally) {
                             Text(text = type.emoji, style = MaterialTheme.typography.displayLarge)
                             Spacer(modifier = Modifier.height(16.dp))
                             Text(text = type.displayName, style = MaterialTheme.typography.headlineMedium)
                             Spacer(modifier = Modifier.height(8.dp))
                             
                             if (analysis != null) {
                                 Text(
                                     text = "Analysis is being processed...",
                                     style = MaterialTheme.typography.bodyLarge,
                                     color = MaterialTheme.colorScheme.primary
                                 )
                                 Spacer(modifier = Modifier.height(8.dp))
                                 Text(
                                     text = "This may take a few moments depending on the chat size.",
                                     style = MaterialTheme.typography.bodySmall,
                                     color = MaterialTheme.colorScheme.onSurfaceVariant,
                                     textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                 )
                             } else {
                                 Text(
                                     text = "No analysis found.",
                                     style = MaterialTheme.typography.bodyMedium
                                 )
                                 Spacer(modifier = Modifier.height(16.dp))
                                 com.ch3x.chatlyzer.ui.components.GradientButton(
                                     text = "Start Analysis",
                                     onClick = { viewModel.createAnalysis(chatId) }
                                 )
                             }
                         }
                    }
                }
            }
            }
        }
    }
}
