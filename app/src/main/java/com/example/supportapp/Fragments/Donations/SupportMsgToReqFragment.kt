package com.example.supportapp.Fragments.Donations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentSupportMsgToReqBinding

class supportMsgToReqFragment : DialogFragment() {

    private lateinit var binding: FragmentSupportMsgToReqBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSupportMsgToReqBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerEvents()

    }

    private fun registerEvents() {
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }
}