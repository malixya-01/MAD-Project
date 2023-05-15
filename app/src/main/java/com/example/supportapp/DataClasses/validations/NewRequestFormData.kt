package com.example.supportapp.DataClasses.validations

data class NewRequestFormData(
    private var title: String,
    private var location: String,
    private var contactNumber: String,
    private var description: String,
    private var bankDetails: String
){
    private val urlPattern = "(http(s)?://)?([\\w-]+\\.)+[\\w-]+\\.+[a-z]+(/[/?%&=]*)?"

    fun validateTitle(): ValidationResult {
        return if(title.isEmpty()){
            ValidationResult.Empty("Title should not be empty")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateLocation(): ValidationResult {
        return if(title.isEmpty()){
            ValidationResult.Empty("Location should not be empty")
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

    fun validateDescription(): ValidationResult {
        return if(description.isEmpty()){
            ValidationResult.Empty("Description should not be empty")
        } else {
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
