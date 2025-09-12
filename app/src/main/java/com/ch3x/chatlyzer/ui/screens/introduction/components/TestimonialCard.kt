package com.ch3x.chatlyzer.ui.screens.introduction.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch3x.chatlyzer.ui.components.StarRating

@Composable
fun TestimonialCard(
    testimonial: Testimonial,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(192.dp) // Fixed height for all cards
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TestimonialHeader(testimonial = testimonial)

            Spacer(modifier = Modifier.height(8.dp))

            TestimonialContent(content = testimonial.content)
        }
    }
}

@Composable
private fun TestimonialHeader(
    testimonial: Testimonial,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        // Profile image
        Image(
            painter = painterResource(id = testimonial.imageResId),
            contentScale = ContentScale.Crop,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Name and rating container with proper constraints
        Column(
            modifier = Modifier.weight(0.8f) // Take available space
        ) {
            // Name with overflow handling
            Text(
                text = testimonial.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Rating below name to prevent overflow
            StarRating(count = testimonial.rating)
        }
    }
}

@Composable
private fun TestimonialContent(
    content: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = content,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        maxLines = 6,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
} 