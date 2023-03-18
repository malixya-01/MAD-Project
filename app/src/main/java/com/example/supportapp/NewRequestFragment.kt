package com.example.supportapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class NewRequestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_new_request, container, false)

        val button = view.findViewById<Button>(R.id.btnBack)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_newRequestFragment_to_requestsFragment)
        }


        return view
    }
}