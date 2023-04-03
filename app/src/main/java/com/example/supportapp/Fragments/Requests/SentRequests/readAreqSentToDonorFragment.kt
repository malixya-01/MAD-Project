package com.example.supportapp.Fragments.Requests.SentRequests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R

class readAreqSentToDonorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_read_areq_sent_to_donor, container, false)

        val btnEdit = view.findViewById<Button>(R.id.btnEdit)
        btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_readAreqSentToDonorFragment_to_updateReqAddedToDonorFragment)
        }


        return view
    }
}