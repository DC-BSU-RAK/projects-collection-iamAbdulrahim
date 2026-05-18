package com.ritualbuilder

// Simple in-memory shared state between fragments
object AppState {
    val studySessions = mutableListOf<StudySession>()
    val checklistItems = mutableListOf<ChecklistItem>()
}
