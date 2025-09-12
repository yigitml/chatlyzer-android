package com.ch3x.chatlyzer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme

@Composable
fun Header(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    showCloseButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onCloseClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.size(48.dp)) {
            when {
                showBackButton -> IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
                showCloseButton -> IconButton(
                    onClick = onCloseClick,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Chatlyzer AI",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            //Spacer(modifier = Modifier.width(8.dp))

            //LogoComponent()
        }

        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
fun LogoComponent(modifier: Modifier = Modifier, size: Int = 36) {
    Box(
        modifier = modifier
            .size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_sort_by_size),
            contentDescription = "Chart Icon",
            modifier = Modifier.size((size*2/3).dp)
        )
    }
}

@Preview
@Composable
private fun PreviewHeader() {
    ChatlyzerTheme {
        Header()
    }
}