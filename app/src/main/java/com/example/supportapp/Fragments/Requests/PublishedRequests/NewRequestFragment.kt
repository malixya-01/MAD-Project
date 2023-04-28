package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentNewRequestBinding

class NewRequestFragment : Fragment() {

    private lateinit var binding: FragmentNewRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init()
        registerEvents()
    }

    /*private fun init() {
        TODO("Not yet implemented")
    }*/

    private fun registerEvents() {
        binding.btnSubmitNewReq.setOnClickListener {
            Toast.makeText(context, "Yooo", Toast.LENGTH_SHORT).show()
        }
    }

}