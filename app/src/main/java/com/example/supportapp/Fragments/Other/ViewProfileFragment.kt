package com.example.supportapp.Fragments.Other

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R

class ViewProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_profile, container, false)


        var resetPwd = view.findViewById<Button>(R.id.resetPwd)
        resetPwd.setOnClickListener {
            findNavController().navigate(R.id.action_viewProfileFragment_to_resetPasswordActivity)
        }


        return view
    }
}