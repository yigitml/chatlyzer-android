package com.ch3x.chatlyzer.ui.screens.sign_in.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch3x.chatlyzer.R
import com.ch3x.chatlyzer.ui.theme.GoldenYellow

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        shape = RoundedCornerShape(128.dp),
        onClick = onClick,
        enabled = !isLoading,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        colors = ButtonDefaults.buttonColors(containerColor = GoldenYellow)
    ) {
        GoogleSignInButtonContent()
    }
}

@Composable
private fun GoogleSignInButtonContent(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.google_icon),
        contentDescription = "Google Icon",
        modifier = Modifier.size(32.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
        text = "Sign In With Google",
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    )
} 