package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.DataClasses.RequestsData
import com.example.supportapp.DataClasses.User
import com.example.supportapp.DataClasses.validations.NewRequestFormData
import com.example.supportapp.DataClasses.validations.ValidationResult
import com.example.supportapp.DataClasses.validations.newFrFormData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentNewRequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewRequestFragment : Fragment() {

    private lateinit var binding: FragmentNewRequestBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var databaseRefUsers : DatabaseReference
    private lateinit var dialog: Dialog
    private lateinit var uid: String
    private lateinit var user: User
    private var isValidationSuccess = false

    private var userName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerEvents()
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        //to access the fundraising
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("requests")

        //to access the users
        databaseRefUsers = FirebaseDatabase.getInstance().getReference("users")

        //retrieve the username
        if (uid.isNotEmpty()){
            getUserData()
        }
    }

    private fun registerEvents() {
        binding.btnSubmitNewReq.setOnClickListener {

            var title = binding.tvNewReqTitle.text.toString()
            var city = binding.tvNewReqCity.text.toString()
            var contactNo = binding.tvNewReqContactNumber.text.toString()
            var description = binding.tvNewReqDescription.text.toString()
            var bankDetails = binding.tvNewReqBankDetails.text.toString()

            validateForm(title, city, contactNo, description, bankDetails)

            if(isValidationSuccess){
                showProgressBar()
                var date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                //Id for new fr
                var reqId = databaseRef.push().key!!
                //create a FundraisingData object
                val reqData = RequestsData(title,userName,city,description,contactNo,uid, bankDetails, date, reqId)
                databaseRef.child(reqId).setValue(reqData).addOnCompleteListener {
                    if (it.isSuccessful){
                        hideProgressBar()
                        findNavController().navigate(R.id.action_newRequestFragment_to_requestsFragment2)
                        Toast.makeText(context, "Your fundraiser added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getUserData() {
        databaseRefUsers.child(uid).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                user = snapshot.getValue(User::class.java)!!
                userName = user.name
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Failed to retrieve user name", Toast.LENGTH_SHORT).show()
            }
        })
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
                binding.tvNewReqTitle.error = titleValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {}
        }

        when (locationValidation) {
            is ValidationResult.Empty -> {
                binding.tvNewReqCity.error = locationValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {}
        }

        when (contactNoValidation) {
            is ValidationResult.Empty -> {
                binding.tvNewReqContactNumber.error = contactNoValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                binding.tvNewReqContactNumber.error = contactNoValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
        }
        when (descriptionValidation) {
            is ValidationResult.Empty -> {
                binding.tvNewReqDescription.error = descriptionValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {}
        }


        when (bankDetailsValidation) {
            is ValidationResult.Empty -> {
                binding.tvNewReqBankDetails.error = bankDetailsValidation.errorMessage
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



    private fun showProgressBar(){
        dialog = Dialog( this@NewRequestFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }
}