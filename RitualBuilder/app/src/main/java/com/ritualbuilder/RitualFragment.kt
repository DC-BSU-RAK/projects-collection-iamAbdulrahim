package com.ritualbuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ritualbuilder.databinding.FragmentRitualBinding

class RitualFragment : Fragment() {

    private var _b: FragmentRitualBinding? = null
    private val b get() = _b!!

    private val sequence = mutableListOf<RitualAction>()
    private lateinit var actionAdapter: ActionAdapter
    private lateinit var sequenceAdapter: SequenceAdapter
    private lateinit var prefs: Prefs

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentRitualBinding.inflate(i, c, false); return b.root
    }

    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)
        prefs = Prefs(requireContext())

        // Load saved ritual
        val savedNames = prefs.savedRitual
        val loaded = savedNames.mapNotNull { name ->
            RitualData.allActions.find { it.name == name }
        }
        sequence.addAll(loaded)
        syncChecklist()

        // Action grid
        actionAdapter = ActionAdapter(RitualData.allActions) { action ->
            if (sequence.any { it.name == action.name }) {
                sequence.removeAll { it.name == action.name }
            } else {
                sequence.add(action)
            }
            updateSequenceUI()
            syncChecklist()
        }
        actionAdapter.setSelected(savedNames)
        b.rvActions.layoutManager = GridLayoutManager(requireContext(), 3)
        b.rvActions.adapter = actionAdapter

        // Sequence list
        sequenceAdapter = SequenceAdapter(sequence) { action ->
            sequence.remove(action)
            actionAdapter.setSelected(sequence.map { it.name })
            updateSequenceUI()
            syncChecklist()
        }
        b.rvSequence.layoutManager = LinearLayoutManager(requireContext())
        b.rvSequence.adapter = sequenceAdapter

        updateSequenceUI()

        b.btnSaveRitual.setOnClickListener {
            prefs.savedRitual = sequence.map { it.name }
            syncChecklist()
            Snackbar.make(b.root, "Ritual saved!", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun updateSequenceUI() {
        sequenceAdapter.notifyDataSetChanged()
        b.tvEmptySequence.visibility = if (sequence.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun syncChecklist() {
        AppState.checklistItems.clear()
        AppState.checklistItems.addAll(sequence.map { ChecklistItem(it) })
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
