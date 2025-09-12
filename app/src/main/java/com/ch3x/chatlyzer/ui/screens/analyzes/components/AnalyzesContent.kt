package com.ch3x.chatlyzer.ui.screens.analyzes.components

import android.R.attr.fontWeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch3x.chatlyzer.domain.model.Chat
import com.ch3x.chatlyzer.ui.components.EmptyState
import com.ch3x.chatlyzer.ui.components.ErrorState
import com.ch3x.chatlyzer.ui.components.LoadingState
import com.ch3x.chatlyzer.ui.screens.ScreenState
import com.ch3x.chatlyzer.ui.screens.analyzes.AnalyzesState

@Composable
fun AnalyzesContent(
    state: AnalyzesState,
    chatId: String?,
    onNavigateToAnalysisDetail: (String) -> Unit,
    onCreateAnalysis: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (state.screenState) {
            ScreenState.Loading -> {
                LoadingState()
            }

            ScreenState.Success -> {
                if (state.analyzes.isEmpty()) {
                    EmptyState(
                        title = if (chatId != null) "No analyses for this chat" else "No analyses yet",
                        subtitle = if (chatId != null)
                            "Analyze this chat to see insights"
                        else
                            "Create your first analysis to get started",
                        icon = Icons.Outlined.Analytics,
                        buttonText = "Create Analysis",
                        onButtonClick = onCreateAnalysis
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        state.chat?.title?.let {
                            Column(Modifier.padding(bottom = 8.dp, top = 16.dp),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "Analyzes for chat:",
                                    Modifier.padding(horizontal = 2.dp),
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 22.sp
                                )
                                Text(
                                    state.chat.title,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 22.sp
                                )
                            }
                        }
                        AnalysisList(
                            analyses = state.analyzes,
                            onAnalysisClick = onNavigateToAnalysisDetail,
                            chatId = chatId,
                            modifier = Modifier.padding()
                        )
                    }
                }
            }

            ScreenState.Error -> {
                ErrorState(errorMessage = state.errorMessage)
            }
        }
    }
}