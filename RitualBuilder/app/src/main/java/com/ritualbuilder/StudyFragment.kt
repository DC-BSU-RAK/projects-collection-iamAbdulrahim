package com.ritualbuilder

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ritualbuilder.databinding.FragmentStudyBinding

class StudyFragment : Fragment() {

    private var _b: FragmentStudyBinding? = null
    private val b get() = _b!!

    private var timer: CountDownTimer? = null
    private var isRunning = false
    private var totalMillis = 25 * 60 * 1000L
    private var remainingMillis = totalMillis
    private var focusLevel = 3
    private lateinit var prefs: Prefs
    private lateinit var sessionAdapter: SessionAdapter

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentStudyBinding.inflate(i, c, false); return b.root
    }

    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)
        prefs = Prefs(requireContext())

        // Set default duration from prefs
        setTimerDuration(prefs.defaultDuration * 60 * 1000L)

        // Timer duration buttons
        b.btn25.setOnClickListener { setTimerDuration(25 * 60 * 1000L) }
        b.btn45.setOnClickListener { setTimerDuration(45 * 60 * 1000L) }
        b.btn60.setOnClickListener { setTimerDuration(60 * 60 * 1000L) }

        // Start/pause button
        b.btnStartTimer.setOnClickListener {
            if (isRunning) pauseTimer() else startTimer()
        }

        // Reset button
        b.btnResetTimer.setOnClickListener { resetTimer() }

        // Duration seekbar
        b.seekDuration.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                val mins = maxOf(5, progress)
                b.tvDurationValue.text = "$mins min"
            }
            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        // Star rating for focus
        buildStars()

        // Sessions list
        sessionAdapter = SessionAdapter(AppState.studySessions)
        b.rvSessions.layoutManager = LinearLayoutManager(requireContext())
        b.rvSessions.adapter = sessionAdapter

        // Log session button
        b.btnLogSession.setOnClickListener { logSession() }
    }

    private fun buildStars() {
        b.layoutStars.removeAllViews()
        for (i in 1..5) {
            val star = TextView(requireContext())
            star.text = if (i <= focusLevel) "⭐" else "☆"
            star.textSize = 24f
            star.setPadding(4, 0, 4, 0)
            star.setOnClickListener {
                focusLevel = i
                buildStars()
            }
            b.layoutStars.addView(star)
        }
    }

    private fun setTimerDuration(millis: Long) {
        if (isRunning) return
        totalMillis = millis
        remainingMillis = millis
        updateTimerDisplay(millis)
    }

    private fun startTimer() {
        isRunning = true
        b.btnStartTimer.text = "Pause"
        timer = object : CountDownTimer(remainingMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingMillis = millisUntilFinished
                updateTimerDisplay(millisUntilFinished)
            }
            override fun onFinish() {
                isRunning = false
                b.btnStartTimer.text = "Start"
                b.tvTimer.text = "Done!"
                b.tvTimerLabel.text = "break time"
                remainingMillis = totalMillis
            }
        }.start()
    }

    private fun pauseTimer() {
        timer?.cancel()
        isRunning = false
        b.btnStartTimer.text = "Resume"
    }

    private fun resetTimer() {
        timer?.cancel()
        isRunning = false
        remainingMillis = totalMillis
        b.btnStartTimer.text = "Start"
        b.tvTimerLabel.text = "focus"
        updateTimerDisplay(totalMillis)
    }

    private fun updateTimerDisplay(millis: Long) {
        val mins = (millis / 1000) / 60
        val secs = (millis / 1000) % 60
        b.tvTimer.text = "%02d:%02d".format(mins, secs)
    }

    private fun logSession() {
        val subject = b.etSubject.text.toString().trim()
        if (subject.isEmpty()) {
            Snackbar.make(b.root, "Please enter a subject", Snackbar.LENGTH_SHORT).show()
            return
        }
        val duration = maxOf(5, b.seekDuration.progress)
        val session = StudySession(subject, duration, focusLevel)
        AppState.studySessions.add(0, session)
        sessionAdapter.notifyItemInserted(0)
        b.etSubject.text?.clear()
        b.seekDuration.progress = 25
        b.tvDurationValue.text = "25 min"
        focusLevel = 3
        buildStars()
        Snackbar.make(b.root, "Session logged!", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        timer?.cancel()
        super.onDestroyView()
        _b = null
    }
}
