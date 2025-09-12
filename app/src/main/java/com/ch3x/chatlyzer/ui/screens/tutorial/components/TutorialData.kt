package com.ch3x.chatlyzer.ui.screens.tutorial.components

import android.os.Bundle
import androidx.navigation.NavType
import com.ch3x.chatlyzer.R

data class TutorialStep(
    val name: String, 
    val imageRes: Int
)

enum class Platform {
    WHATSAPP,
    INSTAGRAM
}

val PlatformType = object : NavType<Platform>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Platform? {
        return Platform.valueOf(bundle.getString(key) ?: "")
    }

    override fun parseValue(value: String): Platform {
        return Platform.valueOf(value)
    }

    override fun put(bundle: Bundle, key: String, value: Platform) {
        bundle.putString(key, value.name)
    }
}

object TutorialDataProvider {
    fun getTutorialSteps(): Map<Platform, List<TutorialStep>> = mapOf(
        Platform.WHATSAPP to listOf(
            TutorialStep("1. ", R.drawable.whatsapp_logo),
            /*TutorialStep("2. ", R.drawable.whatsapp_logo),
            TutorialStep("3. ", R.drawable.whatsapp_logo),
            TutorialStep("4. ", R.drawable.whatsapp_logo),
            TutorialStep("5. ", R.drawable.whatsapp_logo),
            TutorialStep("6. ", R.drawable.whatsapp_logo),
            TutorialStep("7. ", R.drawable.whatsapp_logo),
            TutorialStep("8. ", R.drawable.whatsapp_logo),

             */
        ),
        Platform.INSTAGRAM to listOf(
            TutorialStep("1. ", R.drawable.instagram_logo),
            TutorialStep("2. ", R.drawable.instagram_logo),
            TutorialStep("3. ", R.drawable.instagram_logo),
            TutorialStep("4. ", R.drawable.instagram_logo),
            TutorialStep("5. ", R.drawable.instagram_logo),
            TutorialStep("6. ", R.drawable.instagram_logo),
            TutorialStep("7. ", R.drawable.instagram_logo),
            TutorialStep("8. ", R.drawable.instagram_logo),
        ),
    )
} 