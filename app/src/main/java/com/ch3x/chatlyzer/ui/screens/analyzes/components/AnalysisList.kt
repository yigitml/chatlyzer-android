package com.ch3x.chatlyzer.ui.screens.analyzes.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.domain.model.Analysis
import com.ch3x.chatlyzer.ui.components.animations.AnimatedListItem

@Composable
fun AnalysisList(
    analyses: List<Analysis>,
    onAnalysisClick: (String) -> Unit,
    chatId: String?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        if (chatId == null) {
            item {
                Text(
                    text = "All Analyses",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        
        items(
            items = analyses,
            key = { it.id },
            contentType = { "analysis_item" }
        ) { analysis ->
            val index = analyses.indexOf(analysis)
            AnimatedListItem(
                index = index,
                staggerDelay = 50
            ) {
                AnalysisItem(analysis, onAnalysisClick)
            }
        }
    }
} 