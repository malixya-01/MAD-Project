package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentEditARequestBinding
import com.example.supportapp.databinding.FragmentUpdateFrBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class editARequestFragment : Fragment() {
    private lateinit var binding: FragmentEditARequestBinding
    private lateinit var auth: FirebaseAuth
    private val args by navArgs<editARequestFragmentArgs>()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dialog: Dialog
    private lateinit var currentFr: FundraisingData
    private var isValidationSuccess = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditARequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        //registerEvents()
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
            .child("requests")

        //bind data recieved to text input fields
        binding.tvTitle.setText(args.currentReq.title.toString())
        binding.tvDes.setText(args.currentReq.description.toString())
        binding.tvLocation.setText(args.currentReq.location.toString())
        binding.tvPhoneNo.setText(args.currentReq.phoneNo.toString())
        binding.tvBankDetails.setText(args.currentReq.bankDetails.toString())
    }

    /*private fun registerEvents() {
        TODO("Not yet implemented")
    }*/
}