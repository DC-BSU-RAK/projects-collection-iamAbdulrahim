package com.ritualbuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ritualbuilder.databinding.SheetInfoBinding

class InfoSheet : BottomSheetDialogFragment() {
    private var _b: SheetInfoBinding? = null
    private val b get() = _b!!
    companion object { const val TAG = "InfoSheet" }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = SheetInfoBinding.inflate(i, c, false); return b.root
    }
    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)
        b.btnCloseInfo.setOnClickListener { dismiss() }
    }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
