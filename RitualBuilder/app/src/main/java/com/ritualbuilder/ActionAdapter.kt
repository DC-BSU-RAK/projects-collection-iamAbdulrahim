package com.ritualbuilder

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ritualbuilder.databinding.ItemActionBinding

class ActionAdapter(
    private val actions: List<RitualAction>,
    private val onActionClick: (RitualAction) -> Unit
) : RecyclerView.Adapter<ActionAdapter.ActionVH>() {

    private val selected = mutableSetOf<String>()

    inner class ActionVH(val binding: ItemActionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ActionVH(ItemActionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = actions.size

    override fun onBindViewHolder(holder: ActionVH, position: Int) {
        val action = actions[position]
        val isSelected = selected.contains(action.name)
        with(holder.binding) {
            tvActionEmoji.text = action.emoji
            tvActionName.text = action.name
            val bg = GradientDrawable()
            bg.shape = GradientDrawable.RECTANGLE
            bg.cornerRadius = 40f
            if (isSelected) {
                bg.setColor(android.graphics.Color.parseColor("#0F3D2E"))
                bg.setStroke(3, android.graphics.Color.parseColor("#1D9E75"))
                tvActionName.setTextColor(android.graphics.Color.parseColor("#1D9E75"))
            } else {
                bg.setColor(android.graphics.Color.parseColor("#1A1A20"))
                bg.setStroke(1, android.graphics.Color.parseColor("#2D2D35"))
                tvActionName.setTextColor(android.graphics.Color.parseColor("#888888"))
            }
            actionRoot.background = bg
            actionRoot.setOnClickListener {
                if (selected.contains(action.name)) selected.remove(action.name)
                else selected.add(action.name)
                notifyItemChanged(position)
                onActionClick(action)
            }
        }
    }

    fun deselectAll() { selected.clear(); notifyDataSetChanged() }
    fun setSelected(names: List<String>) { selected.clear(); selected.addAll(names); notifyDataSetChanged() }
}
