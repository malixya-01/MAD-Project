package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.supportapp.databinding.FragmentViewReqAllDonorsBinding

class ViewReqAllDonorsFragment: Fragment() {

    private lateinit var binding: FragmentViewReqAllDonorsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentViewReqAllDonorsBinding.inflate(inflater, container, false)
        return binding.root
    }



}