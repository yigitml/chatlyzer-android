package com.ch3x.chatlyzer.ui.screens.introduction.components

import com.ch3x.chatlyzer.R

data class Testimonial(
    val name: String,
    val subtitle: String,
    val content: String,
    val imageResId: Int = R.drawable.left_laurel, // TODO
    val rating: Int = 5
) {
    companion object {
        fun getSampleTestimonials() = listOf(
            Testimonial(
                rating = 4,
                name = "Elise de Camps",
                subtitle = "The friend who always texts first",
                content = "I always felt like I was carrying my friendships, but Chatlyzer confirmed it—80% of the messages were mine. It stung, but now I know who's actually worth my time."
            ),
            Testimonial(
                rating = 5,
                name = "Mark Johnson",
                subtitle = "Long-distance relationship survivor",
                content = "Chatlyzer showed us we exchanged over 50,000 messages in just one year of dating long-distance. Seeing our communication patterns helped us through the tough times."
            ),
            Testimonial(
                rating = 5,
                name = "Sophia Chen",
                subtitle = "Digital marketing specialist",
                content = "As someone who manages multiple client conversations, Chatlyzer helped me identify which clients required more attention and when I was most responsive. Game-changer for my workflow!"
            )
        )
    }
} 