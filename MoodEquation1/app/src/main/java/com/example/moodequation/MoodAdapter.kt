package com.moodequation

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moodequation.databinding.ItemMoodBinding

class MoodAdapter(
    private val moods: List<Mood>,
    private val onMoodClick: (Mood) -> Unit
) : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {

    private val selectedMoods = mutableSetOf<Mood>()

    inner class MoodViewHolder(val binding: ItemMoodBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val binding = ItemMoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        val mood = moods[position]
        val isSelected = selectedMoods.contains(mood)
        with(holder.binding) {
            tvMoodEmoji.text = mood.emoji
            tvMoodName.text = mood.name
            val bg = GradientDrawable()
            bg.shape = GradientDrawable.RECTANGLE
            bg.cornerRadius = 28f
            if (isSelected) {
                bg.setColor(Color.parseColor(mood.bgColor))
                bg.setStroke(4, Color.parseColor(mood.accentColor))
                tvMoodName.setTextColor(Color.parseColor(mood.accentColor))
                moodTileRoot.alpha = 1f
            } else {
                bg.setColor(Color.parseColor("#1a1a20"))
                bg.setStroke(2, Color.parseColor("#2d2d35"))
                tvMoodName.setTextColor(Color.parseColor("#888888"))
                moodTileRoot.alpha = 0.85f
            }
            moodTileRoot.background = bg
            moodTileRoot.setOnClickListener { onMoodClick(mood) }
        }
    }

    override fun getItemCount() = moods.size

    fun selectMood(mood: Mood) { selectedMoods.add(mood); notifyItemChanged(moods.indexOf(mood)) }
    fun deselectMood(mood: Mood) { selectedMoods.remove(mood); notifyItemChanged(moods.indexOf(mood)) }
    fun clearSelections() {
        val prev = selectedMoods.toSet(); selectedMoods.clear()
        prev.forEach { notifyItemChanged(moods.indexOf(it)) }
    }
}