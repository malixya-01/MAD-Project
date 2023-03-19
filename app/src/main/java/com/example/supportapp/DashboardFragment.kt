package com.example.supportapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController

class DashboardFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        //buttons
        var myRequetsBtn = view.findViewById<ConstraintLayout>(R.id.myRequstsBtn)
        var myDonationsBtn = view.findViewById<ConstraintLayout>(R.id.myDonationsBtn)
        var myFundrasingsBtn = view.findViewById<ConstraintLayout>(R.id.myFundrasingsBtn)
        var profileBtn = view.findViewById<ImageView>(R.id.imUserDp)


        //rederecting
        myRequetsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_myRequstsFragment)
        }

        myDonationsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_myDonationsFragment)
        }

        myFundrasingsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_viewSingleRequestFragment)
        }

        profileBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_viewProfileFragment)
        }

        return view

    }


}