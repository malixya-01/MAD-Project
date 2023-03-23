package com.example.supportapp.Fragments.Donations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R


class viewADonationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_a_donation, container, false)

        val btnEdit = view.findViewById<Button>(R.id.btnEdit)

        btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_viewADonationFragment_to_updateDonationFragment)
        }

        return view
    }
}