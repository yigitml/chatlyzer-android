package com.ch3x.chatlyzer.ui.screens.chat_create.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.screens.chat_create.AnalysisType
import com.ch3x.chatlyzer.ui.screens.chat_create.ChatCreateEvent

@Composable
fun AnalysisTypeSelector(
  analysisType: AnalysisType,
  onEvent: (ChatCreateEvent) -> Unit
) {

  Row(Modifier.fillMaxWidth()) {
    FilterChip(
      modifier = Modifier.padding(horizontal = 4.dp).weight(0.33f),
      onClick = {
        onEvent(ChatCreateEvent.ChangeAnalysisType(AnalysisType.NORMAL))
      },
      label = {
        Text("Normal")
      },
      selected = analysisType == AnalysisType.NORMAL,
      leadingIcon = if (analysisType == AnalysisType.NORMAL) {
        {
          Icon(imageVector = Icons.Filled.Done, contentDescription = "", modifier = Modifier)
        }
      } else
        null
    )

    FilterChip(
      modifier = Modifier.padding(horizontal = 4.dp).weight(0.33f),
      onClick = {
        onEvent(ChatCreateEvent.ChangeAnalysisType(AnalysisType.PRIVACY))
      },
      label = {
        Text("Privacy")
      },
      selected = analysisType == AnalysisType.PRIVACY,
      leadingIcon = if (analysisType == AnalysisType.PRIVACY) {
        {
          Icon(imageVector = Icons.Filled.Done, contentDescription = "", modifier = Modifier)
        }
      } else
        null
    )

    FilterChip(
      modifier = Modifier.padding(horizontal = 4.dp).weight(0.33f),
      onClick = {
        onEvent(ChatCreateEvent.ChangeAnalysisType(AnalysisType.GHOST))
      },
      label = {
        Text("Ghost")
      },
      selected = analysisType == AnalysisType.GHOST,
      leadingIcon = if (analysisType == AnalysisType.GHOST) {
        {
          Icon(imageVector = Icons.Filled.Done, contentDescription = "", modifier = Modifier)
        }
      } else
        null
    )
  }
}