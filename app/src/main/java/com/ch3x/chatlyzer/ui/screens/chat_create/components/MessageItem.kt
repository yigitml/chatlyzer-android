package com.ch3x.chatlyzer.ui.screens.chat_create.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.domain.model.Message
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MessageItem(
    message: Message,
    onUpdate: (Message) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Message",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(message.timestamp),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    IconButton(
                        onClick = onRemove,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Remove Message",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            OutlinedTextField(
                value = message.sender,
                onValueChange = { onUpdate(message.copy(sender = it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Sender") },
                placeholder = { Text("Enter sender name...") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = message.content,
                onValueChange = { onUpdate(message.copy(content = it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Content") },
                placeholder = { Text("Enter message content...") },
                minLines = 2,
                maxLines = 4
            )
        }
    }
} 