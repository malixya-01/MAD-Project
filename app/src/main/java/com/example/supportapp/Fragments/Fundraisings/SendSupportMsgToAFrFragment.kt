package com.example.supportapp.Fragments.Fundraisings

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.supportapp.DataClasses.supportFundraiserData
import com.example.supportapp.DataClasses.validations.ValidationResult
import com.example.supportapp.DataClasses.validations.sendMessageFormData
import com.example.supportapp.Fragments.Requests.SentRequests.addReqtoTheDonorFragment
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentSendSupportMsgToAFrBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class sendSupportMsgToAFrFragment : DialogFragment() {

    private lateinit var binding: FragmentSendSupportMsgToAFrBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dialog: Dialog
    private var uid: String? = null
    private var isFormValidationSuccess = false
    private lateinit var listner: dialogSubmitButtonClickedListner

    fun setListner( listner: dialogSubmitButtonClickedListner){
        this.listner = listner
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSendSupportMsgToAFrBinding.inflate(inflater, container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerEvents()
    }

    private fun registerEvents(){
        //submit data
        binding.btnSupFrSubmit.setOnClickListener {

            //bind editText views to variables
            val phoneNo = binding.etSupFrPhone.text.toString()
            val email = binding.etSupFrEmail.text.toString()
            val message = binding.etSupFrMsg.text.toString()

            //validate form
            validateForm(phoneNo, email, message)

            //calling save method if validation is success
            if (isFormValidationSuccess){
                listner.onSave(phoneNo, email, message)
            }
        }

        //close btn
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    interface dialogSubmitButtonClickedListner{
        fun onSave(phone: String?, email: String?, message: String)
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