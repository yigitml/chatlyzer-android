package com.ch3x.chatlyzer.ui.screens.introduction.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroductionContent(
    onNavigateToSignInPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val testimonials = Testimonial.getSampleTestimonials()
    val pagerState = rememberPagerState(pageCount = { testimonials.size }, initialPage = 1)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        IntroHeader()

        Spacer(modifier = Modifier.height(32.dp))

        StatsSection()

        Spacer(modifier = Modifier.height(32.dp))

        SupportedAppsSection()

        Spacer(modifier = Modifier.height(32.dp))

        TestimonialsSection(
            testimonials = testimonials,
            pagerState = pagerState
        )

        Spacer(modifier = Modifier.height(16.dp))

        GetStartedButton(onClick = onNavigateToSignInPage)

        Spacer(modifier = Modifier.height(16.dp))
    }
} 