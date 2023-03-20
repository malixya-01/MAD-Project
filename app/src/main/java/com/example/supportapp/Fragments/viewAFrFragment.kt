package com.example.supportapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R

class viewAFrFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_a_fr, container, false)

        val updateBtn = view.findViewById<Button>(R.id.btnRequest)
        updateBtn.setOnClickListener {
            findNavController().navigate(R.id.action_viewAFrFragment_to_updateFrFragment)
        }

        return view
    }
}