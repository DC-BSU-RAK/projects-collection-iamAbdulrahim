package com.ritualbuilder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ritualbuilder.databinding.ItemSequenceBinding

class SequenceAdapter(
    private val sequence: MutableList<RitualAction>,
    private val onRemove: (RitualAction) -> Unit
) : RecyclerView.Adapter<SequenceAdapter.SeqVH>() {

    inner class SeqVH(val binding: ItemSequenceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SeqVH(ItemSequenceBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = sequence.size

    override fun onBindViewHolder(holder: SeqVH, position: Int) {
        val action = sequence[position]
        with(holder.binding) {
            tvSeqEmoji.text = action.emoji
            tvSeqName.text = action.name
            tvRemove.setOnClickListener { onRemove(action) }
        }
    }
}
