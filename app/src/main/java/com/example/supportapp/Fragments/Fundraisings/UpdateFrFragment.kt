package com.example.supportapp.Fragments.Fundraisings

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentUpdateFrBinding
import com.example.supportapp.DataClasses.validations.ValidationResult
import com.example.supportapp.DataClasses.validations.updateFrFormData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UpdateFrFragment : Fragment() {

    private lateinit var binding: FragmentUpdateFrBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dialog: Dialog
    private lateinit var currentFr: FundraisingData
    private var isValidationSuccess = false


    private val args : UpdateFrFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateFrBinding.inflate(inflater, container, false)
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
            .child("fundraising")

        //retrieve current fr data and populate editTexts
        getCurrentFundraiser()



    }

    private fun getCurrentFundraiser() {
        showProgressBar()


        databaseReference.child(args.currentFrId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to fr model class
                currentFr = snapshot.getValue(FundraisingData::class.java)!!
                //bind data
                bindData(currentFr)
                hideProgressBar()
            }
            override fun onCancelled(error: DatabaseError) {
                hideProgressBar()
                Toast.makeText(activity, "Failed to retrieve current fundraiser", Toast.LENGTH_SHORT).show()
            }
        })



    }

    private fun bindData(currentFr: FundraisingData) {
        binding.etUpdateFrTitle.setText(currentFr.title.toString())
        binding.etUpdateFrDescription.setText(currentFr.description.toString())
        binding.etUpdateFrExpectedAmt.setText(currentFr.expectedAmt.toString())
        binding.etUpdateFrCollectedAmt.setText(currentFr.collectedAmt.toString())
        binding.etUpdateFrContactNo.setText(currentFr.contactNo.toString())
        binding.etUpdateFrContactNo.setText(currentFr.contactNo.toString())
        binding.etUpdateFrEmail.setText(currentFr.email.toString())
        binding.etUpdateFrWebsite.setText(currentFr.website.toString())
        binding.etUpdateFrBankDetails.setText(currentFr.bankDetails.toString())
    }

    private fun registerEvents() {
        binding.btnUpdateFrSubmit.setOnClickListener {
            validateForm()

            if(isValidationSuccess){
                //create hashMap to keep key and value pairs
                val map = HashMap<String,Any>()

                //add data to hashMap
                map["title"] = binding.etUpdateFrTitle.text.toString()
                map["description"] = binding.etUpdateFrDescription.text.toString()
                map["expectedAmt"] = binding.etUpdateFrExpectedAmt.text.toString()
                map["collectedAmt"] = binding.etUpdateFrCollectedAmt.text.toString()
                map["contactNo"] = binding.etUpdateFrContactNo.text.toString()
                map["email"] = binding.etUpdateFrEmail.text.toString()
                map["website"] = binding.etUpdateFrWebsite.text.toString()
                map["bankDetails"] = binding.etUpdateFrBankDetails.text.toString()

                //update database from hashMap
                databaseReference.child(args.currentFrId).updateChildren(map).addOnCompleteListener {
                    if( it.isSuccessful){
                        Toast.makeText(context, "Fundraiser updated", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_updateFrFragment_to_myFundraisingsFragment)
                    } else {
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun validateForm(){
        //set isValidationSuccess to false
        isValidationSuccess = false

        //variable to keep count of passed validations
        var count = 0

        //create a updateFrFormData class object
        var myForm = updateFrFormData(
            binding.etUpdateFrTitle.text.toString(),
            binding.etUpdateFrDescription.text.toString(),
            binding.etUpdateFrExpectedAmt.text.toString(),
            binding.etUpdateFrCollectedAmt.text.toString(),
            binding.etUpdateFrContactNo.text.toString(),
            binding.etUpdateFrEmail.text.toString(),
            binding.etUpdateFrWebsite.text.toString(),
            binding.etUpdateFrBankDetails.text.toString(),
        )

        //assign each validation method to variables
        val titleValidation = myForm.validateTitle()
        val descriptionValidation = myForm.validateDescription()
        val expectedAmtValidation = myForm.validateExpectedAmount()
        val collectedAmountValidation = myForm.validateCollectedAmount()
        val contactNoValidation = myForm.validateContactNumber()
        val emailValidation = myForm.validateEmail()
        val websiteValidation = myForm.validateWebsite()
        val bankDetailsValidation = myForm.validateBankDetails()

        when (titleValidation) {
            is ValidationResult.Empty -> {
                binding.etUpdateFrTitle.error = titleValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {}
        }

        when (descriptionValidation) {
            is ValidationResult.Empty -> {
                binding.etUpdateFrDescription.error = descriptionValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {}
        }

        when (expectedAmtValidation) {
            is ValidationResult.Empty -> {
                binding.etUpdateFrExpectedAmt.error = expectedAmtValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                binding.etUpdateFrExpectedAmt.error = expectedAmtValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
        }

        when (collectedAmountValidation) {
            is ValidationResult.Empty -> {
                binding.etUpdateFrCollectedAmt.error = collectedAmountValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                binding.etUpdateFrCollectedAmt.error = collectedAmountValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
        }

        when (contactNoValidation) {
            is ValidationResult.Empty -> {
                binding.etUpdateFrContactNo.error = contactNoValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                binding.etUpdateFrContactNo.error = contactNoValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
        }

        when (emailValidation) {
            is ValidationResult.Empty -> {
                binding.etUpdateFrEmail.error = emailValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                binding.etUpdateFrEmail.error = emailValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
        }

        when (websiteValidation) {
            is ValidationResult.Empty -> {}
            is ValidationResult.Invalid -> {
                binding.etUpdateFrWebsite.error = websiteValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
        }

        when (bankDetailsValidation) {
            is ValidationResult.Empty -> {
                binding.etUpdateFrBankDetails.error = bankDetailsValidation.errorMessage
            }
            is ValidationResult.Invalid -> {}
            is ValidationResult.Valid -> {
                count++
            }
        }

        //set isValidationSuccess to true if all validations are success
        if (count == 8){
            isValidationSuccess = true
        }


    }

    private fun showProgressBar(){
        dialog = Dialog(this@UpdateFrFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }

}
