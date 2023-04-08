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
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.DataClasses.User
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentNewFundraiserBinding
import com.example.supportapp.models.validations.newFrFormData
import com.example.supportapp.models.validations.ValidationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewFundraiserFragment : Fragment() {

    private lateinit var binding: FragmentNewFundraiserBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var databaseRef2: DatabaseReference
    private lateinit var dialog: Dialog
    private lateinit var uid: String
    private lateinit var user: User
    private var isValidationSuccess = false
    private var userName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewFundraiserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvents()
    }

    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        //to access the fundraising
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("fundraising")

        //to access the users
        databaseRef2 = FirebaseDatabase.getInstance().getReference("users")

        //retrieve the username
        if (uid.isNotEmpty()){
            getUserData()
        }

    }

    private fun registerEvents() {

        binding.btnNewFrSubmit.setOnClickListener {
            validateForm()
            if (isValidationSuccess) {

                showProgressBar()

                var title = binding.etNewFrTitle.text.toString()
                var description = binding.etNewFrDescription.text.toString()
                var expectedAmt = binding.etNewFrExpectedAmt.text.toString()
                var collectedAmt = binding.etNewFrCollectedAmt.text.toString()
                var contactNo = binding.etNewFrContactNo.text.toString()
                var email = binding.etNewFrEmail.text.toString()
                var website = binding.etNewFrWebsite.text.toString()
                var bankDetails = binding.etNewFrBankDetails.text.toString()
                var verifiedStatus:Boolean = false
                var date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                val fr = FundraisingData(title, description, expectedAmt, collectedAmt, contactNo, email, website, bankDetails, userName, verifiedStatus, uid, date)

                databaseRef.push().setValue(fr).addOnCompleteListener {
                    if (it.isSuccessful){
                        hideProgressBar()
                        findNavController().navigate(R.id.action_newFundraiserFragment_to_fundrasingFragment)
                        Toast.makeText(context, "Your fundraiser added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
                //Toast.makeText(activity, "Validations passed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserData() {
        databaseRef2.child(uid).addValueEventListener(object : ValueEventListener {

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

    fun validateForm() {
        //set isValidationSuccess to false
        isValidationSuccess = false

        //variable to keep count of passed validations
        var count = 0

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
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {}
        }

        when (descriptionValidation) {
            is ValidationResult.Empty -> {
                binding.etNewFrDescription.error = descriptionValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {}
        }

        when (expectedAmtValidation) {
            is ValidationResult.Empty -> {
                binding.etNewFrExpectedAmt.error = expectedAmtValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                binding.etNewFrExpectedAmt.error = expectedAmtValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
        }

        when (collectedAmountValidation) {
            is ValidationResult.Empty -> {
                binding.etNewFrCollectedAmt.error = collectedAmountValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                binding.etNewFrCollectedAmt.error = collectedAmountValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
        }

        when (contactNoValidation) {
            is ValidationResult.Empty -> {
                binding.etNewFrContactNo.error = contactNoValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                binding.etNewFrContactNo.error = contactNoValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
        }

        when (emailValidation) {
            is ValidationResult.Empty -> {
                binding.etNewFrEmail.error = emailValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                binding.etNewFrEmail.error = emailValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
        }

        when (websiteValidation) {
            is ValidationResult.Empty -> {}
            is ValidationResult.Invalid -> {
                binding.etNewFrWebsite.error = websiteValidation.errorMessage
            }
            is ValidationResult.Valid -> {
                count++
            }
        }

        when (bankDetailsValidation) {
            is ValidationResult.Empty -> {
                binding.etNewFrBankDetails.error = bankDetailsValidation.errorMessage
            }
            is ValidationResult.Invalid -> {}
            is ValidationResult.Valid -> {
                count++
            }
        }

        //set isValidationSuccess to true if all validations are success
        if (count == 8) {
            isValidationSuccess = true
        }


    }

    private fun showProgressBar(){
        dialog = Dialog( this@NewFundraiserFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }

}
