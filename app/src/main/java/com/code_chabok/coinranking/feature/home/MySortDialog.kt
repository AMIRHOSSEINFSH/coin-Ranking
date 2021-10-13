package com.code_chabok.coinranking.feature.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.code_chabok.coinranking.common.OnChangeSort
import com.code_chabok.coinranking.databinding.MySortDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.Exception

class MySortDialog: BottomSheetDialogFragment() {

    private lateinit var onChangeListener: OnChangeSort
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
         onChangeListener = parentFragment  as OnChangeSort
        }
        catch (e: Exception){

        }
    }

    private lateinit var binding: MySortDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MySortDialogBinding.inflate(inflater,container,false)
       return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.radioGroup.isVisible = false

        binding.desc.setOnClickListener {
            binding.desc.isChecked = true
            onChangeListener.onResult(true)
            dismiss()
        }
        binding.asc.setOnClickListener {
            binding.asc.isChecked = true
            onChangeListener.onResult(false)
            dismiss()
        }

    }


}