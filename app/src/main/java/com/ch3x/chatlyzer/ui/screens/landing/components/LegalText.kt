package com.ch3x.chatlyzer.ui.screens.landing.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LegalText(modifier: Modifier = Modifier) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Gray)) {
            append("By continuing, you understand and agree to our ")

            pushStringAnnotation(tag = "terms", annotation = "terms")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Terms & Conditions")
            }
            pop()

            append(" and ")

            pushStringAnnotation(tag = "privacy", annotation = "privacy")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Privacy Policy.")
            }
            pop()
        }
    }

    Text(
        text = annotatedString,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        modifier = modifier.padding(horizontal = 16.dp)
    )
} 