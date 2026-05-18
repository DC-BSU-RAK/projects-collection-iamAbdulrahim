package com.ritualbuilder

data class RitualAction(
    val name: String,
    val emoji: String
)

data class StudySession(
    val subject: String,
    val durationMinutes: Int,
    val focusLevel: Int  // 1-5
)

data class ChecklistItem(
    val action: RitualAction,
    var isChecked: Boolean = false
)

object RitualData {
    val allActions = listOf(
        RitualAction("breathe",  "🌬️"),
        RitualAction("stretch",  "🧘"),
        RitualAction("journal",  "📓"),
        RitualAction("walk",     "🚶"),
        RitualAction("hydrate",  "💧"),
        RitualAction("meditate", "🪷"),
        RitualAction("observe",  "👁️"),
        RitualAction("reflect",  "🌙"),
        RitualAction("read",     "📖"),
        RitualAction("draw",     "✏️"),
        RitualAction("gratitude","🙏"),
        RitualAction("rest",     "😴")
    )
}
