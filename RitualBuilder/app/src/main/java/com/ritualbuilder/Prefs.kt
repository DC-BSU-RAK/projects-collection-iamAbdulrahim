package com.ritualbuilder

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("ritual_prefs", Context.MODE_PRIVATE)

    // Dark mode
    var isDarkMode: Boolean
        get() = prefs.getBoolean("dark_mode", true)
        set(v) = prefs.edit().putBoolean("dark_mode", v).apply()

    // Accent colour: "green", "purple", "amber"
    var accentColor: String
        get() = prefs.getString("accent_color", "green") ?: "green"
        set(v) = prefs.edit().putString("accent_color", v).apply()

    // Default study duration in minutes
    var defaultDuration: Int
        get() = prefs.getInt("default_duration", 25)
        set(v) = prefs.edit().putInt("default_duration", v).apply()

    // Show streaks toggle
    var showStreaks: Boolean
        get() = prefs.getBoolean("show_streaks", true)
        set(v) = prefs.edit().putBoolean("show_streaks", v).apply()

    // Saved ritual sequence — stored as comma-separated names
    var savedRitual: List<String>
        get() {
            val raw = prefs.getString("saved_ritual", "") ?: ""
            return if (raw.isEmpty()) emptyList() else raw.split(",")
        }
        set(v) = prefs.edit().putString("saved_ritual", v.joinToString(",")).apply()
}
