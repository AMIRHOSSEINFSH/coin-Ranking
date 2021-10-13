package com.code_chabok.coinranking.feature.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code_chabok.coinranking.databinding.MySortDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MySortDialog: BottomSheetDialogFragment() {


    override fun onAttach(context: Context) {
        super.onAttach(context)
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

    }


}