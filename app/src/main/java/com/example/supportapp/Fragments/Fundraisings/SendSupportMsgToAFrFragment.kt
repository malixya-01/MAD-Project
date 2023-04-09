package com.example.supportapp.Fragments.Fundraisings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentSendSupportMsgToAFrBinding

class sendSupportMsgToAFrFragment : Fragment() {

    private lateinit var binding: FragmentSendSupportMsgToAFrBinding
    val args: sendSupportMsgToAFrFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSendSupportMsgToAFrBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSupFrMsg.setText(args.currentFrId)
    }
}