package com.moodequation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moodequation.databinding.FragmentInfoSheetBinding

class InfoBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentInfoSheetBinding? = null
    private val binding get() = _binding!!

    companion object { const val TAG = "InfoBottomSheet" }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInfoSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCloseSheet.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}