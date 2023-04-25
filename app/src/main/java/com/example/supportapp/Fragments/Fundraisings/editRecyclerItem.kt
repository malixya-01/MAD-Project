package com.example.supportapp.Fragments.Fundraisings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.supportapp.DataClasses.supportFundraiserData
import com.example.supportapp.DataClasses.validations.ValidationResult
import com.example.supportapp.DataClasses.validations.sendMessageFormData
import com.example.supportapp.databinding.FragmentEditRecyclerItemBinding

class editRecyclerItem : DialogFragment() {

    private lateinit var binding: FragmentEditRecyclerItemBinding
    private lateinit var listener: onUpdateBtnClickeListner
    private var isFormValidationSuccess = false
    private var supMsg: supportFundraiserData? = null

    fun setListner(listner: onUpdateBtnClickeListner) {
        this.listener = listner
    }

    companion object {
        const val TAG = "editRecyclerItem"

        @JvmStatic
        fun newInstance(
            msgId: String,
            phone: String,
            email: String,
            message: String,
            supporterId: String,
            date: String
        ) = editRecyclerItem().apply {
            arguments = Bundle().apply {
                putString("msgId", msgId)
                putString("supporterId", supporterId)
                putString("email", email)
                putString("phone", phone)
                putString("message", message)
                putString("date", date)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditRecyclerItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            //create new object from arguments
            supMsg = supportFundraiserData(
                arguments?.getString("msgId").toString(),
                arguments?.getString("supporterId").toString(),
                arguments?.getString("email").toString(),
                arguments?.getString("phone").toString(),
                arguments?.getString("message").toString(),
                arguments?.getString("date").toString()
            )
        }
        init()
        registerEvents()
    }

    private fun init() {
        //bind values to text views
        binding.etPhone.setText(supMsg?.phoneNumber)
        binding.etEmail.setText(supMsg?.email)
        binding.etMsg.setText(supMsg?.message)

    }

    private fun registerEvents() {

        binding.btnUpdate.setOnClickListener {

            //bind editText views to variables
            val phoneNo = binding.etPhone.text.toString()
            val email = binding.etEmail.text.toString()
            val message = binding.etMsg.text.toString()

            //validate form
            validateForm(phoneNo, email, message)

            if (isFormValidationSuccess) {

                //update values in the currentItem object
                supMsg?.phoneNumber = phoneNo
                supMsg?.email = email
                supMsg?.message = message

                //pass the updated object to the update method
                listener.onUpdate(supMsg!!)

            }
        }

        //close btn
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    interface onUpdateBtnClickeListner {
        fun onUpdate(updatedSupMsgData: supportFundraiserData)
    }


    private fun validateForm(phone: String, email: String, message: String) {
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
        when (contactNoValidation) {
            is ValidationResult.Empty -> emptyContactDetailsCount++
            is ValidationResult.Invalid -> binding.etPhone.error = contactNoValidation.errorMessage
            is ValidationResult.Valid -> contactDetailsValidity++
        }

        when (emailValidation) {
            is ValidationResult.Empty -> emptyContactDetailsCount++
            is ValidationResult.Invalid -> binding.etEmail.error = emailValidation.errorMessage
            is ValidationResult.Valid -> contactDetailsValidity++
        }

        when (messageValidation) {
            is ValidationResult.Empty -> binding.etMsg.error = messageValidation.errorMessage
            is ValidationResult.Invalid -> {}
            is ValidationResult.Valid -> otherDetailsValidity++
        }

        //make sure any of the contact details is filled
        if (emptyContactDetailsCount != 2) {

            //get number of entered contact fields
            var enteredContactDetails = 0
            when (emptyContactDetailsCount) {
                0 -> enteredContactDetails = 2
                1 -> enteredContactDetails = 1
            }

            //set isValidationSuccess to true if all validations are success
            if ((contactDetailsValidity == enteredContactDetails) && (otherDetailsValidity != 0)) {
                isFormValidationSuccess = true
            }
        } else {
            Toast.makeText(context, "Please fill contact number or email", Toast.LENGTH_SHORT)
                .show()
        }
    }
}