package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R

class viewaRequestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_view_a_request, container, false)

        var btnUpdate = view.findViewById<Button>(R.id.btnRequest)
        btnUpdate.setOnClickListener {
            findNavController().navigate(R.id.action_viewSingleRequestFragment_to_editARequestFragment2)
        }



        return view
    }
}