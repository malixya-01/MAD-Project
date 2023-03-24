package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R

class viewARequestAllUsersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_view_a_single_req_all_users, container, false)

        var btnSup = view.findViewById<Button>(R.id.btnSup)

        btnSup.setOnClickListener {
            findNavController().navigate(R.id.action_viewASingleReqAllUsersFragment2_to_supportMsgToReqFragment)
        }

        return view
    }
}