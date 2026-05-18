package com.ritualbuilder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ritualbuilder.databinding.ItemChecklistBinding

class ChecklistAdapter(
    private val items: MutableList<ChecklistItem>,
    private val onChecked: () -> Unit
) : RecyclerView.Adapter<ChecklistAdapter.CheckVH>() {

    inner class CheckVH(val binding: ItemChecklistBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CheckVH(ItemChecklistBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CheckVH, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvCheckEmoji.text = item.action.emoji
            tvCheckName.text = item.action.name
            checkAction.isChecked = item.isChecked
            checkAction.setOnCheckedChangeListener { _, checked ->
                item.isChecked = checked
                onChecked()
            }
        }
    }
}
