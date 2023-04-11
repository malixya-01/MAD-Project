package com.example.supportapp.Fragments.Requests.SentRequests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentAddReqtoTheDonorBinding

class addReqtoTheDonorFragment : Fragment() {

    private lateinit var binding: FragmentAddReqtoTheDonorBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddReqtoTheDonorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
}