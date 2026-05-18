package com.ritualbuilder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ritualbuilder.databinding.ItemSessionBinding

class SessionAdapter(
    private val sessions: List<StudySession>
) : RecyclerView.Adapter<SessionAdapter.SessionVH>() {

    inner class SessionVH(val binding: ItemSessionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SessionVH(ItemSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = sessions.size

    override fun onBindViewHolder(holder: SessionVH, position: Int) {
        val s = sessions[position]
        with(holder.binding) {
            tvSessionSubject.text = s.subject
            tvSessionDetails.text = "${s.durationMinutes} min"
            tvSessionFocus.text = "⭐".repeat(s.focusLevel)
        }
    }
}
