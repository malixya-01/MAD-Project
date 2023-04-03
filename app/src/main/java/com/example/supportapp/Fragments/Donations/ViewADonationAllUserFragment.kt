package com.example.supportapp.Fragments.Donations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ViewADonationAllUserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_a_donation_all_user, container, false)

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

        val btnReq =view.findViewById<Button>(R.id.btnReq)
        btnReq.setOnClickListener {
            findNavController().navigate(R.id.action_viewADonationAllUserFragment_to_addReqtoTheDonorFragment)
        }


        return view
    }
}