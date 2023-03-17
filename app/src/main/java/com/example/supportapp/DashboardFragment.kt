package com.example.supportapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class DashboardFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        var myRequetsBtn = view.findViewById<ConstraintLayout>(R.id.myRequstsBtn)

        myRequetsBtn.setOnClickListener {
            Toast.makeText(context,"Hello, Sagoor",Toast.LENGTH_LONG).show()
        }

        return view

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


}