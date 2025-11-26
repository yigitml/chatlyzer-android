package com.ch3x.chatlyzer.ui.screens.introduction.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.PaginationDots
import com.ch3x.chatlyzer.ui.components.animations.AnimatedListItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TestimonialsSection(
    testimonials: List<Testimonial>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            val testimonial = testimonials[page]
            AnimatedListItem(
                index = page,
                staggerDelay = 50
            ) {
                TestimonialCard(testimonial = testimonial)
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        ) {
            PaginationDots(
                currentPage = pagerState.currentPage,
                pageCount = testimonials.size
            )
        }
    }
} 