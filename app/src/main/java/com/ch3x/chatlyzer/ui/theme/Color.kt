package com.ch3x.chatlyzer.ui.theme
import androidx.compose.ui.graphics.Color

// --- NEW PALETTE DEFINITIONS ---
val GoldenYellow = Color(0xFFF4C156) // The color you requested
val WarmAmber = Color(0xFFFF9F1C)    // Complementary Gradient End
val DeepSlate = Color(0xFF101418)    // New Background
val SoftSlate = Color(0xFF1C2229)    // New Surface
val TealAccent = Color(0xFF2EC4B6)   // New Secondary

// --- MAPPING (What you change in the codebase) ---

// 1. PrimaryPink was your main color. We map it to the Yellow.
val PrimaryPink = GoldenYellow
val Pink = GoldenYellow // Handle your alias

// 2. PrimaryPurple was the gradient end. We map it to Amber.
val PrimaryPurple = WarmAmber

// 3. SecondaryOrange was your accent. We map it to Teal for contrast.
val SecondaryOrange = TealAccent

// 4. Dark Theme Backgrounds (Updated to the new Slate look)
val BackgroundDark = DeepSlate
val SurfaceDark = SoftSlate
val SurfaceLight = Color(0xFF2B343F)
val SurfaceGlass = Color(0x25F4C156) // Yellow tinted glass

// 5. Text Colors
val TextWhite = Color(0xFFFFFFFF)
val TextGray = Color(0xFF9CA3AF) // Adjusted to a cooler grey to match slate

// 6. Functional Colors
val SuccessGreen = Color(0xFF00E676)
val ErrorRed = Color(0xFFFF4C4C) // Softer red
val WhatsAppGreen = Color(0xFF25D366)

// 7. Gradients (Updated logic)
// This will now create a Yellow -> Amber gradient instead of Pink -> Purple
val PrimaryGradientColors = listOf(GoldenYellow, WarmAmber)

// Instagram Gradient (Updated to Sunset style)
val InstagramGradientStart = GoldenYellow
val InstagramGradientEnd = Color(0xFFE76F51)
val InstagramGradientColors = listOf(InstagramGradientStart, InstagramGradientEnd)