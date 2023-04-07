package com.example.supportapp.models

import com.example.supportapp.models.validations.ValidationResult

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
     /*fun validateDescription(): ValidationResult {
        return if(description.isEmpty()){
            ValidationResult.Empty("Description should not be empty")
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
    fun validateDescription(): ValidationResult {
        return if(description.isEmpty()){
            ValidationResult.Empty("Description should not be empty")
        } else {
            ValidationResult.Valid
        }
    }*/

}