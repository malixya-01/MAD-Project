package com.example.supportapp.Fragments.Fundraisings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class viewAFrFragment : Fragment() {

    // variables to function progress bar
    lateinit var progressBar: ProgressBar






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_a_fr, container, false)

        progressBar = view.findViewById(R.id.frProgressBar)
        progressBar.progress = 165000



        val updateBtn = view.findViewById<FloatingActionButton>(R.id.updateBtn)
        updateBtn.setOnClickListener {
            findNavController().navigate(R.id.action_viewAFrFragment_to_updateFrFragment)
        }

        return view
    }
}