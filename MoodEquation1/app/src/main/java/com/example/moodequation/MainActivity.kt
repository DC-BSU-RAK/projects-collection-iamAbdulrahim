package com.moodequation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.moodequation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val selectedMoods = mutableListOf<Mood>()
    private lateinit var adapter: MoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MoodEquation)   // ← only line added
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupClickListeners()
        updateEquationDisplay()
    }

    private fun setupRecyclerView() {
        adapter = MoodAdapter(MoodData.moods) { mood -> onMoodToggled(mood) }
        binding.rvMoodGrid.layoutManager = GridLayoutManager(this, 3)
        binding.rvMoodGrid.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.btnInfo.setOnClickListener {
            InfoBottomSheet().show(supportFragmentManager, InfoBottomSheet.TAG)
        }
        binding.btnCompute.setOnClickListener {
            if (selectedMoods.size >= 2) computeEquation()
        }
        binding.btnClear.setOnClickListener {
            selectedMoods.clear()
            adapter.clearSelections()
            updateEquationDisplay()
            clearResult()
        }
    }

    private fun onMoodToggled(mood: Mood) {
        if (selectedMoods.contains(mood)) {
            selectedMoods.remove(mood); adapter.deselectMood(mood)
        } else {
            if (selectedMoods.size >= 4) { shakeCard(); return }
            selectedMoods.add(mood); adapter.selectMood(mood)
        }
        updateEquationDisplay(); clearResult()
    }

    private fun updateEquationDisplay() {
        binding.btnCompute.isEnabled = selectedMoods.size >= 2
        if (selectedMoods.isEmpty()) {
            binding.tvEquationTokens.text = "select moods below..."
            binding.tvEquationTokens.alpha = 0.4f
        } else {
            binding.tvEquationTokens.alpha = 1f
            val tokens = selectedMoods.joinToString("  +  ") { it.name }
            binding.tvEquationTokens.animate().alpha(0f).setDuration(80).withEndAction {
                binding.tvEquationTokens.text = tokens
                binding.tvEquationTokens.animate().alpha(1f).setDuration(200).start()
            }.start()
        }
    }

    private fun computeEquation() {
        val result = EquationEngine.compute(selectedMoods)
        binding.tvResult.animate().alpha(0f).translationY(10f).setDuration(100).withEndAction {
            binding.tvResult.text = "\"${result.phrase}\""
            binding.tvResult.animate().alpha(1f).translationY(0f).setDuration(280)
                .setInterpolator(OvershootInterpolator(1.1f)).start()
        }.start()
        binding.viewColorBar.visibility = View.VISIBLE
        val color = Color.parseColor(result.color)
        val widthAnim = ValueAnimator.ofInt(0, binding.cardEquation.width - 64)
        widthAnim.duration = 650
        widthAnim.interpolator = AccelerateDecelerateInterpolator()
        widthAnim.addUpdateListener {
            val lp = binding.viewColorBar.layoutParams
            lp.width = it.animatedValue as Int
            binding.viewColorBar.layoutParams = lp
        }
        val colorAnim = ValueAnimator.ofArgb(Color.parseColor("#333344"), color)
        colorAnim.duration = 650
        colorAnim.addUpdateListener { binding.viewColorBar.setBackgroundColor(it.animatedValue as Int) }
        AnimatorSet().apply { playTogether(widthAnim, colorAnim); start() }
        binding.viewColorSwatch.visibility = View.VISIBLE
        binding.viewColorSwatch.setBackgroundColor(color)
        binding.viewColorSwatch.animate().alpha(1f).setDuration(400)
            .setInterpolator(AccelerateDecelerateInterpolator()).start()
    }

    private fun clearResult() {
        binding.tvResult.text = ""
        binding.viewColorBar.visibility = View.INVISIBLE
        val lp = binding.viewColorBar.layoutParams; lp.width = 0
        binding.viewColorBar.layoutParams = lp
        binding.viewColorSwatch.visibility = View.INVISIBLE
        binding.viewColorSwatch.alpha = 0f
    }

    private fun shakeCard() {
        ObjectAnimator.ofFloat(binding.cardEquation, "translationX",
            0f, 14f, -14f, 10f, -10f, 0f).apply { duration = 320; start() }
    }
}