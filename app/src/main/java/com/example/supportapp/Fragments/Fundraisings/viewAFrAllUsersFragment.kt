package com.example.supportapp.Fragments.Fundraisings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class viewAFrAllUsersFragment : Fragment() {

    // variables to function progress bar
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_view_a_fr_all_users, container, false)

        //Variables to set up FAB onclick icon change
        val fab = view.findViewById<FloatingActionButton>(R.id.btnSave)
        var flag = true // true if first icon is visible, false if second one is visible.

        //varibles to hold toast msgs
        val itemAdded = resources.getString(R.string.itemAdded)
        val itemRemoved = resources.getString(R.string.itemRemoved)

        fab.setOnClickListener {
            if (flag) {
                fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.selected_bookmark))
                flag = false
                Toast.makeText(requireContext(), itemAdded, Toast.LENGTH_SHORT).show()

            } else {
                fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.unselected_bookmark))
                flag = true
                Toast.makeText(requireContext(), itemRemoved, Toast.LENGTH_SHORT).show()

            }
        }

        progressBar = view.findViewById(R.id.frProgressBar)
        progressBar.progress = 210000


/*
        val btnSup = view.findViewById<Button>(R.id.btnSup)
        btnSup.setOnClickListener {
            findNavController().navigate(R.id.action_viewAFrFragment_to_updateFrFragment)
        }
*/








        return view
    }
}