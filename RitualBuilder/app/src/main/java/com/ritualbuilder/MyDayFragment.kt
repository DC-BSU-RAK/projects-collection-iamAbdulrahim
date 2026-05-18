package com.ritualbuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ritualbuilder.databinding.FragmentMydayBinding
import java.text.SimpleDateFormat
import java.util.*

class MyDayFragment : Fragment() {

    private var _b: FragmentMydayBinding? = null
    private val b get() = _b!!

    private lateinit var checklistAdapter: ChecklistAdapter
    private lateinit var sessionAdapter: SessionAdapter

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentMydayBinding.inflate(i, c, false); return b.root
    }

    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)

        // Today's date
        val fmt = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
        b.tvTodayDate.text = fmt.format(Date())

        // Checklist
        checklistAdapter = ChecklistAdapter(AppState.checklistItems) { updateStats() }
        b.rvRitualChecklist.layoutManager = LinearLayoutManager(requireContext())
        b.rvRitualChecklist.adapter = checklistAdapter

        // Study sessions
        sessionAdapter = SessionAdapter(AppState.studySessions)
        b.rvStudySummary.layoutManager = LinearLayoutManager(requireContext())
        b.rvStudySummary.adapter = sessionAdapter

        updateStats()
    }

    override fun onResume() {
        super.onResume()
        // Refresh when user comes back to this tab
        checklistAdapter.notifyDataSetChanged()
        sessionAdapter.notifyDataSetChanged()
        updateStats()
    }

    private fun updateStats() {
        val total = AppState.checklistItems.size
        val done = AppState.checklistItems.count { it.isChecked }
        b.tvRitualCount.text = "$done/$total"

        val studyMins = AppState.studySessions.sumOf { it.durationMinutes }
        b.tvStudyTotal.text = "${studyMins} min"
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
