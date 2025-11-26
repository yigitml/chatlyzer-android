package com.ch3x.chatlyzer.ui.screens.premium.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubscriptionCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DiscountBadge()
            
            SubscriptionTitle()
            
            SubscriptionPrice()
            
            Spacer(modifier = Modifier.height(8.dp))
            
            SubscriptionDescription()
        }
    }
}

@Composable
private fun DiscountBadge(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        Text(
            text = "Save 50%",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun SubscriptionTitle(modifier: Modifier = Modifier) {
    Text(
        text = "Weekly",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
private fun SubscriptionPrice(modifier: Modifier = Modifier) {
    Text(
        text = "TRY170 / week",
        fontSize = 18.sp,
        modifier = modifier
    )
}

@Composable
private fun SubscriptionDescription(modifier: Modifier = Modifier) {
    Text(
        text = "1 Premium Analysis Per Day (7 Total)",
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
} 