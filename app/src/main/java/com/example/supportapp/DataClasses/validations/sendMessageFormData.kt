package com.example.supportapp.DataClasses.validations

class sendMessageFormData(
    private var contactNumber: String,
    private var email: String,
    private var message: String,
) {

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    fun validateContactNumber(): ValidationResult {
        return if(contactNumber.isEmpty()) {
            ValidationResult.Empty("")
        } else if (contactNumber.length != 10){
            ValidationResult.Invalid("Enter a valid contact number")
        } else{
            ValidationResult.Valid
        }
    }

    fun validateEmail(): ValidationResult {
        return if(email.isEmpty()) {
            ValidationResult.Empty("")
        } else if(!email.matches(emailPattern.toRegex()) ){
            ValidationResult.Invalid("Enter a valid email address")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateMessage(): ValidationResult {
        return if(message.isEmpty()){
            ValidationResult.Empty("Message should not be empty")
        } else {
            ValidationResult.Valid
        }
    }



}