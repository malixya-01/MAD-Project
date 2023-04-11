package com.example.supportapp.Fragments.Fundraisings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.supportapp.DataClasses.validations.ValidationResult
import com.example.supportapp.DataClasses.validations.sendMessageFormData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentEditRecyclerItemBinding

class editRecyclerItem : DialogFragment() {

    private lateinit var binding: FragmentEditRecyclerItemBinding

    private var isFormValidationSuccess = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditRecyclerItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerEvents()
    }

    private fun registerEvents() {

        binding.btnSupFrSubmit.setOnClickListener {

            //bind editText views to variables
            val phoneNo = binding.etSupFrPhone.text.toString()
            val email = binding.etSupFrEmail.text.toString()
            val message = binding.etSupFrMsg.text.toString()

            //validate form
            validateForm(phoneNo, email, message)

            //calling save method if validation is success
            if (isFormValidationSuccess){

            }
        }

        //close btn
        binding.btnClose.setOnClickListener {
            dismiss()
        }

    }


    private fun validateForm(phone: String, email: String, message: String){
        //set isValidationSuccess to false
        isFormValidationSuccess = false

        //variable to keep count of passed validations
        var emptyContactDetailsCount = 0 // to track contact details validity
        var contactDetailsValidity = 0 // to track all fields validity
        var otherDetailsValidity = 0 //to track other details validity

        //create a updateFrFormData class object
        var myForm = sendMessageFormData(phone, email, message)

        //assign each validation method to variables
        val contactNoValidation = myForm.validateContactNumber()
        val emailValidation = myForm.validateEmail()
        val messageValidation = myForm.validateMessage()

        //actions based on each validation result
        when(contactNoValidation) {
            is ValidationResult.Empty -> emptyContactDetailsCount++
            is ValidationResult.Invalid -> binding.etSupFrPhone.error = contactNoValidation.errorMessage
            is ValidationResult.Valid -> contactDetailsValidity++
        }

        when(emailValidation) {
            is ValidationResult.Empty -> emptyContactDetailsCount++
            is ValidationResult.Invalid -> binding.etSupFrEmail.error = emailValidation.errorMessage
            is ValidationResult.Valid -> contactDetailsValidity++
        }

        when(messageValidation) {
            is ValidationResult.Empty -> binding.etSupFrMsg.error = messageValidation.errorMessage
            is ValidationResult.Invalid -> {}
            is ValidationResult.Valid -> otherDetailsValidity++
        }

        //make sure any of the contact details is filled
        if (emptyContactDetailsCount != 2 ){

            //get number of entered contact fields
            var enteredContactDetails = 0
            when(emptyContactDetailsCount){
                0 -> enteredContactDetails = 2
                1 -> enteredContactDetails = 1
            }

            //set isValidationSuccess to true if all validations are success
            if( (contactDetailsValidity == enteredContactDetails) && (otherDetailsValidity != 0)) {
                isFormValidationSuccess = true
            }
        } else {
            Toast.makeText(context, "Please fill contact number or email", Toast.LENGTH_SHORT).show()
        }
    }
}