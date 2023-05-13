package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.DataClasses.RequestsData
import com.example.supportapp.DataClasses.validations.NewRequestFormData
import com.example.supportapp.DataClasses.validations.ValidationResult
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
    private lateinit var updatedData : RequestsData
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
        registerEvents()
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

    private fun registerEvents() {
        binding.btnUpdate.setOnClickListener {
            var title = binding.tvTitle.text.toString()
            var city = binding.tvLocation.text.toString()
            var contactNo = binding.tvPhoneNo.text.toString()
            var description = binding.tvDes.text.toString()
            var bankDetails = binding.tvBankDetails.text.toString()

            //new object to store updated data
            updatedData = args.currentReq //initialize with old data


            validateForm(title, city, contactNo, description, bankDetails)

            if(isValidationSuccess){
                //create hashMap to keep key and value pairs
                val map = HashMap<String,Any>()
                map["title"] = binding.tvTitle.text.toString()
                map["location"] = binding.tvLocation.text.toString()
                map["phoneNo"] = binding.tvPhoneNo.text.toString()
                map["description"] = binding.tvDes.text.toString()
                map["bankDetails"] = binding.tvBankDetails.text.toString()

                //update object with new data
                updatedData.title = binding.tvTitle.text.toString()
                updatedData.location= binding.tvLocation.text.toString()
                updatedData.phoneNo = binding.tvPhoneNo.text.toString()
                updatedData.description = binding.tvDes.text.toString()
                updatedData.bankDetails = binding.tvBankDetails.text.toString()

                //update database from hashMap
                databaseReference.child(args.currentReq.reqId!!).updateChildren(map).addOnCompleteListener {
                    if( it.isSuccessful){
                        Toast.makeText(context, "Request updated", Toast.LENGTH_SHORT).show()
                        var action = editARequestFragmentDirections.actionEditARequestFragment2ToViewSingleRequestFragment(updatedData)
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }


        }
    }

    fun validateForm(title: String, location: String,phone: String, des: String,bank: String) {
        //set isValidationSuccess to false
        isValidationSuccess = false

        //variable to keep count of passed validations
        var count = 0

        //create a newFrFormData class object
        var myForm = NewRequestFormData(title, location, phone, des, bank)

        //assign each validation method to variables
        val titleValidation = myForm.validateTitle()
        val locationValidation = myForm.validateLocation()
        val contactNoValidation = myForm.validateContactNumber()
        val descriptionValidation = myForm.validateDescription()
        val bankDetailsValidation = myForm.validateBankDetails()

        when (titleValidation) {
            is ValidationResult.Empty -> {
                binding.tvTitle.error = titleValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {}
        }

        when (locationValidation) {
            is ValidationResult.Empty -> {
                binding.tvLocation.error = locationValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {}
        }

        when (contactNoValidation) {
            is ValidationResult.Empty -> {
                binding.tvPhoneNo.error = contactNoValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                binding.tvPhoneNo.error = contactNoValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
        }
        when (descriptionValidation) {
            is ValidationResult.Empty -> {
                binding.tvDes.error = descriptionValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {}
        }


        when (bankDetailsValidation) {
            is ValidationResult.Empty -> {
                binding.tvBankDetails.error = bankDetailsValidation.errorMessage
            }
            is ValidationResult.Invalid -> {}
            is ValidationResult.Valid -> {
                count++
            }
        }

        //set isValidationSuccess to true if all validations are success
        if (count == 5) {
            isValidationSuccess = true
        }


    }

}