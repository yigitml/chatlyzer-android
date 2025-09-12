package com.ch3x.chatlyzer.ui.components.stat_card

import android.R.attr.text
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    height: Dp = Dp.Unspecified,
    icon: String = ""
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .then(if (height != Dp.Unspecified) Modifier.height(height) else Modifier),
        shape = RoundedCornerShape(30.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            )

            if (value.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = icon,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = value,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}