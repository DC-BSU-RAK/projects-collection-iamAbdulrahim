package com.ritualbuilder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.ritualbuilder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme before super.onCreate
        prefs = Prefs(this)
        AppCompatDelegate.setDefaultNightMode(
            if (prefs.isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        setTheme(R.style.Theme_RitualBuilder)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(RitualFragment())
        }

        // Bottom navigation
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_ritual   -> { loadFragment(RitualFragment()); true }
                R.id.nav_study    -> { loadFragment(StudyFragment()); true }
                R.id.nav_myday    -> { loadFragment(MyDayFragment()); true }
                R.id.nav_settings -> { loadFragment(SettingsFragment()); true }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
