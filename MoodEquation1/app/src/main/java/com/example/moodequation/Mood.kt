package com.moodequation

data class Mood(
    val name: String,
    val emoji: String,
    val accentColor: String,
    val bgColor: String
)

object MoodData {
    val moods = listOf(
        Mood("tender",   "🌸", "#c47fa0", "#2a1a22"),
        Mood("hollow",   "🌑", "#8888aa", "#161620"),
        Mood("electric", "⚡", "#f0c060", "#221a00"),
        Mood("heavy",    "🪨", "#7a8a88", "#141a18"),
        Mood("golden",   "🍂", "#e09050", "#201408"),
        Mood("still",    "🪷", "#80b0a0", "#101e1a"),
        Mood("sharp",    "🔪", "#c0c0d8", "#16161e"),
        Mood("warm",     "🕯", "#e07858", "#200e08"),
        Mood("longing",  "🌊", "#6090c8", "#0c1420"),
        Mood("hazy",     "🌫", "#a0a8b0", "#181a1c"),
        Mood("bold",     "🔥", "#e04040", "#200808"),
        Mood("soft",     "🌙", "#9090c8", "#141428")
    )
}