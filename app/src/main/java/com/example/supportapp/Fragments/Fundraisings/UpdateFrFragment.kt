package com.example.supportapp.Fragments.Fundraisings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentUpdateFrBinding
import com.example.supportapp.models.validations.ValidationResult
import com.example.supportapp.models.validations.updateFrFormData

class UpdateFrFragment : Fragment() {

    private lateinit var binding: FragmentUpdateFrBinding
    private var isValidationSuccess = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateFrBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnUpdateFrSubmit.setOnClickListener {
            validateForm()

            if(isValidationSuccess){
                Toast.makeText(activity, "Validations passed", Toast.LENGTH_SHORT).show()
            }
        }



        return view
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

}
