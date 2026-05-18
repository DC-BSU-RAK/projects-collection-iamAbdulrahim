package com.ritualbuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ritualbuilder.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _b: FragmentSettingsBinding? = null
    private val b get() = _b!!
    private lateinit var prefs: Prefs

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentSettingsBinding.inflate(i, c, false); return b.root
    }

    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)
        prefs = Prefs(requireContext())

        // Load saved prefs into UI
        b.switchDarkMode.isChecked = prefs.isDarkMode
        b.switchStreaks.isChecked = prefs.showStreaks

        // Dark mode toggle — actually switches the app theme
        b.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.isDarkMode = isChecked
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        // Streaks toggle
        b.switchStreaks.setOnCheckedChangeListener { _, isChecked ->
            prefs.showStreaks = isChecked
            Snackbar.make(b.root,
                if (isChecked) "Streaks enabled" else "Streaks hidden",
                Snackbar.LENGTH_SHORT).show()
        }

        // Accent colour buttons
        b.colorGreen.setOnClickListener {
            prefs.accentColor = "green"
            Snackbar.make(b.root, "Accent: Green (restart to apply)", Snackbar.LENGTH_SHORT).show()
        }
        b.colorPurple.setOnClickListener {
            prefs.accentColor = "purple"
            Snackbar.make(b.root, "Accent: Purple (restart to apply)", Snackbar.LENGTH_SHORT).show()
        }
        b.colorAmber.setOnClickListener {
            prefs.accentColor = "amber"
            Snackbar.make(b.root, "Accent: Amber (restart to apply)", Snackbar.LENGTH_SHORT).show()
        }

        // Default duration buttons
        highlightDefaultDuration(prefs.defaultDuration)
        b.btnDefault25.setOnClickListener { prefs.defaultDuration = 25; highlightDefaultDuration(25); Snackbar.make(b.root, "Default: 25 min", Snackbar.LENGTH_SHORT).show() }
        b.btnDefault45.setOnClickListener { prefs.defaultDuration = 45; highlightDefaultDuration(45); Snackbar.make(b.root, "Default: 45 min", Snackbar.LENGTH_SHORT).show() }
        b.btnDefault60.setOnClickListener { prefs.defaultDuration = 60; highlightDefaultDuration(60); Snackbar.make(b.root, "Default: 60 min", Snackbar.LENGTH_SHORT).show() }

        // About / info sheet
        b.cardAbout.setOnClickListener {
            InfoSheet().show(parentFragmentManager, InfoSheet.TAG)
        }
    }

    private fun highlightDefaultDuration(mins: Int) {
        val green = android.graphics.Color.parseColor("#1D9E75")
        val grey = android.graphics.Color.parseColor("#888888")
        b.btnDefault25.setTextColor(if (mins == 25) green else grey)
        b.btnDefault45.setTextColor(if (mins == 45) green else grey)
        b.btnDefault60.setTextColor(if (mins == 60) green else grey)
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
