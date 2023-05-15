package com.example.supportapp.Fragments.Donations

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.supportapp.DataClasses.DonationsData
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.DataClasses.RequestsData
import com.example.supportapp.Fragments.Requests.PublishedRequests.editARequestFragmentArgs
import com.example.supportapp.Fragments.Requests.PublishedRequests.editARequestFragmentDirections
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentEditARequestBinding
import com.example.supportapp.databinding.FragmentUpdateDonationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateDonationFragment : Fragment() {
    private lateinit var binding: FragmentUpdateDonationBinding
    private lateinit var auth: FirebaseAuth
    private val args by navArgs<UpdateDonationFragmentArgs>()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dialog: Dialog
    private lateinit var currentFr: FundraisingData
    private var isValidationSuccess = false
    private lateinit var updatedData : DonationsData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateDonationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerEvents()
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
            .child("donations")

        //bind data recieved to text input fields
        binding.newDonUpTitletv.setText(args.currentDon.title.toString())
        binding.newDonUpDesctv.setText(args.currentDon.description.toString())
        binding.newDonUpCitytv.setText(args.currentDon.location.toString())
        binding.newDonUpContactNumtv.setText(args.currentDon.phoneNo.toString())
    }

    private fun registerEvents() {
        binding.btnDonUp.setOnClickListener {
            var title = binding.newDonUpTitletv.text.toString()
            var city = binding.newDonUpCitytv.text.toString()
            var contactNo = binding.newDonUpContactNumtv.text.toString()
            var description = binding.newDonUpDesctv.text.toString()

            //new object to store updated data
            updatedData = args.currentDon //initialize with old data


            if(title.isEmpty() || city.isEmpty() || contactNo.isEmpty() || description.isEmpty()){

                if(title.isEmpty()){
                    binding.newDonUpTitletv.error = "Enter title"
                }
                if(city.isEmpty()){
                    binding.newDonUpCitytv.error = "Enter location"
                }
                if(contactNo.isEmpty()){
                    binding.newDonUpContactNumtv.error = "Enter contact number"
                }
                if(description.isEmpty()){
                    binding.newDonUpDesctv.error = "Enter donation description"
                }
            } else {
                //create hashMap to keep key and value pairs
                val map = HashMap<String,Any>()
                map["title"] = title
                map["location"] = city
                map["phoneNo"] = contactNo
                map["description"] = description

                //update object with new data
                updatedData.title = title
                updatedData.location= city
                updatedData.phoneNo = contactNo
                updatedData.description = description

                //update database from hashMap
                databaseReference.child(args.currentDon.donId!!).updateChildren(map).addOnCompleteListener {
                    if( it.isSuccessful){
                        Toast.makeText(context, "Donation updated", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}