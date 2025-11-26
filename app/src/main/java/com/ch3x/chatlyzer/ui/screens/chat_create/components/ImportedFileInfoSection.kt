package com.ch3x.chatlyzer.ui.screens.chat_create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.screens.chat_create.ImportedFileInfo
import com.ch3x.chatlyzer.util.FileUtils
import com.ch3x.chatlyzer.ui.components.animations.PopIcon

@Composable
fun ImportedFileInfoSection(
    fileInfo: ImportedFileInfo,
    onClearImport: () -> Unit,
    onChangePlatform: () -> Unit,
    modifier: Modifier = Modifier
) {
    com.ch3x.chatlyzer.ui.components.GlassCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val popTrigger = remember { mutableStateOf(true) }
                    PopIcon(trigger = popTrigger.value) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = com.ch3x.chatlyzer.ui.theme.SuccessGreen
                        )
                    }
                    Text(
                        text = "File Imported",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                IconButton(onClick = onClearImport) {
                    Icon(
                        Icons.Default.Close, 
                        contentDescription = "Clear Import"
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            InfoRow("File", fileInfo.fileName)
            InfoRow("Platform", fileInfo.platform.value.capitalize())
            InfoRow("Messages", "${fileInfo.messageCount}")
            InfoRow("Size", FileUtils.formatFileSize(fileInfo.fileSize))
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedButton(
                onClick = onChangePlatform,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Edit, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Change Platform")
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
} 