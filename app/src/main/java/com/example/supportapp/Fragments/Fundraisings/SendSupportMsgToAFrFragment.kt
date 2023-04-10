package com.example.supportapp.Fragments.Fundraisings

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.supportapp.DataClasses.supportFundraiserData
import com.example.supportapp.DataClasses.validations.ValidationResult
import com.example.supportapp.DataClasses.validations.sendMessageFormData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentSendSupportMsgToAFrBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class sendSupportMsgToAFrFragment : Fragment() {

    private lateinit var binding: FragmentSendSupportMsgToAFrBinding
    val args: sendSupportMsgToAFrFragmentArgs by navArgs()
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dialog: Dialog
    private var uid: String? = null
    private var isFormValidationSuccess = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSendSupportMsgToAFrBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerEvents()
    }



    private fun init(){
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid
        //initialize db ref
        databaseReference = FirebaseDatabase.getInstance().reference.child("supportFundraiser").child(args.currentFrId)
    }

    private fun registerEvents(){

        //submit data
        binding.btnSupFrSubmit.setOnClickListener {
            validateForm()
            if(isFormValidationSuccess){
                Toast.makeText(context, "Validations passed", Toast.LENGTH_SHORT).show()
                showProgressBar()
                //initialize variables
                var phone = binding.etSupFrPhone.text.toString()
                var email = binding.etSupFrEmail.text.toString()
                var msg = binding.etSupFrMsg.text.toString()
                var date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                //Id for new record
                var id = databaseReference.push().key!!

                //supportFr object
                var supFr = supportFundraiserData(id, uid, email, phone, msg, date)

                //push created object to the db
                databaseReference.child(id).setValue(supFr).addOnCompleteListener {
                    if (it.isSuccessful){
                        hideProgressBar()
                        Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show()
                        clearFormData()
                    } else {
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun validateForm(){
        //set isValidationSuccess to false
        isFormValidationSuccess = false

        //variable to keep count of passed validations
        var emptyContactDetailsCount = 0 // to track contact details validity
        var contactDetailsValidity = 0 // to track all fields validity
        var otherDetailsValidity = 0 //to track other details validity

        //create a updateFrFormData class object
        var myForm = sendMessageFormData(
            binding.etSupFrPhone.text.toString(),
            binding.etSupFrEmail.text.toString(),
            binding.etSupFrMsg.text.toString()
        )

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

    private fun clearFormData() {
        binding.etSupFrPhone.text = null
        binding.etSupFrEmail.text = null
        binding.etSupFrMsg.text = null
    }


    private fun showProgressBar(){
        dialog = Dialog( this@sendSupportMsgToAFrFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }
}