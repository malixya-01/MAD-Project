package com.example.supportapp.DataClasses.validations

class newFrFormData(
    private var title: String,
    private var description: String,
    private var expectedAmount: String,
    private var collectedAmount: String,
    private var contactNumber: String,
    private var email: String,
    private var website: String,
    private var bankDetails: String
) {

    private var frMinimumAmount = 100000
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private val urlPattern = "(http(s)?://)?([\\w-]+\\.)+[\\w-]+\\.+[a-z]+(/[/?%&=]*)?"
    fun validateTitle(): ValidationResult {
        return if(title.isEmpty()){
            ValidationResult.Empty("Title should not be empty")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateDescription(): ValidationResult {
        return if(description.isEmpty()){
            ValidationResult.Empty("Description should not be empty")
        } else {
            ValidationResult.Valid
        }
    }
    fun validateExpectedAmount(): ValidationResult {
        return if(expectedAmount.isEmpty()){
            ValidationResult.Empty("Expected amount should not be empty")
        } else if(expectedAmount.toDouble() < frMinimumAmount) {
            ValidationResult.Invalid("Minimum expected amount is Rs.100000")
        } else {
            ValidationResult.Valid
        }
    }
   fun validateCollectedAmount(): ValidationResult {
        return if(collectedAmount.isEmpty()){
            ValidationResult.Empty("Collected amount should not be empty")
        } else if(collectedAmount.toDouble() > expectedAmount.toDouble()) {
            ValidationResult.Invalid("Collected amount should not be greater than expected amount")
        } else {
            ValidationResult.Valid
        }
    }
    fun validateContactNumber(): ValidationResult {
        return if(contactNumber.isEmpty()){
            ValidationResult.Empty("Contact number should not be empty")
        } else if(contactNumber.length != 10) {
            ValidationResult.Invalid("Enter a valid contact number")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateEmail(): ValidationResult {
        return if(email.isEmpty()){
            ValidationResult.Empty("Email address should not be empty")
        } else if(!email.matches(emailPattern.toRegex())) {
            ValidationResult.Invalid("Enter a valid email address")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateWebsite(): ValidationResult {
        return if (website.isNotEmpty() && !website.matches(urlPattern.toRegex())){
            ValidationResult.Invalid("Enter a valid website url")
        }  else {
            ValidationResult.Valid
        }
    }

    fun validateBankDetails(): ValidationResult {
        return if(bankDetails.isEmpty()){
            ValidationResult.Empty("Bank details should not be empty")
        } else {
            ValidationResult.Valid
        }
    }


}