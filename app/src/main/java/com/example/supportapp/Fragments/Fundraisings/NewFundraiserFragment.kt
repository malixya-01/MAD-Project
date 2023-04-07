package com.example.supportapp.Fragments.Fundraisings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.supportapp.databinding.FragmentNewFundraiserBinding
import com.example.supportapp.models.newFrFormData
import com.example.supportapp.models.validations.ValidationResult

class NewFundraiserFragment : Fragment() {

    private lateinit var binding: FragmentNewFundraiserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewFundraiserBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnNewFrSubmit.setOnClickListener {

            //create a newFrFormData class object
            var myForm = newFrFormData(
                binding.etNewFrTitle.text.toString(),
                binding.etNewFrDescription.text.toString(),
                binding.etNewFrExpectedAmt.text.toString(),
                binding.etNewFrCollectedAmt.text.toString(),
                binding.etNewFrContactNo.text.toString(),
                binding.etNewFrEmail.text.toString(),
                binding.etNewFrWebsite.text.toString(),
                binding.etNewFrBankDetails.text.toString(),
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
                    binding.etNewFrTitle.error = titleValidation.errorMessage
                }
                is ValidationResult.Valid -> {}
                is ValidationResult.Invalid -> {}
            }

            when (descriptionValidation) {
                is ValidationResult.Empty -> {
                    binding.etNewFrDescription.error = descriptionValidation.errorMessage
                }
                is ValidationResult.Valid -> {}
                is ValidationResult.Invalid -> {}
            }

            when (expectedAmtValidation) {
                is ValidationResult.Empty -> {
                    binding.etNewFrExpectedAmt.error = expectedAmtValidation.errorMessage
                }
                is ValidationResult.Invalid -> {
                    binding.etNewFrExpectedAmt.error = expectedAmtValidation.errorMessage
                }
                is ValidationResult.Valid -> {}
            }

            when (collectedAmountValidation) {
                is ValidationResult.Empty -> {
                    binding.etNewFrCollectedAmt.error = collectedAmountValidation.errorMessage
                }
                is ValidationResult.Invalid -> {
                    binding.etNewFrCollectedAmt.error = collectedAmountValidation.errorMessage
                }
                is ValidationResult.Valid -> {}
            }

            when (contactNoValidation) {
                is ValidationResult.Empty -> {
                    binding.etNewFrContactNo.error = contactNoValidation.errorMessage
                }
                is ValidationResult.Invalid -> {
                    binding.etNewFrContactNo.error = contactNoValidation.errorMessage
                }
                is ValidationResult.Valid -> {}
            }

            when (emailValidation) {
                is ValidationResult.Empty -> {
                    binding.etNewFrEmail.error = emailValidation.errorMessage
                }
                is ValidationResult.Invalid -> {
                    binding.etNewFrEmail.error = emailValidation.errorMessage
                }
                is ValidationResult.Valid -> {}
            }

            when (websiteValidation) {
                is ValidationResult.Empty -> {}
                is ValidationResult.Invalid -> {
                    binding.etNewFrWebsite.error = websiteValidation.errorMessage
                }
                is ValidationResult.Valid -> {}
            }

            when (bankDetailsValidation) {
                is ValidationResult.Empty -> {
                    binding.etNewFrBankDetails.error = bankDetailsValidation.errorMessage
                }
                is ValidationResult.Invalid -> {}
                is ValidationResult.Valid -> {}
            }
        }

        return view
    }

}
