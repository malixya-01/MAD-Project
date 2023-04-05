package com.example.supportapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.supportapp.DataClasses.User
import com.example.supportapp.databinding.ActivityCreateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing auth and database variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        //Hide action bar
        try {
            this.supportActionBar!!.hide()
        } catch (_: NullPointerException) {
        }

        binding.btnCreateAcc.setOnClickListener{
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etPhone.text.toString()
            val address = binding.etAddress.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPwd = binding.etConfirmPwd.text.toString()

            binding.signUpProgressbar.visibility = View.VISIBLE
            binding.etPasswordLayout.isPasswordVisibilityToggleEnabled = true
            binding.etConfirmPwdLayout.isPasswordVisibilityToggleEnabled = true

            //checking if the input fields are empty
            if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPwd.isEmpty()){
                if(name.isEmpty()){
                    binding.etName.error = "Enter your name"
                }
                if(email.isEmpty()){
                    binding.etEmail.error = "Enter your email"
                }
                if(phone.isEmpty()){
                    binding.etPhone.error = "Enter your phone"
                }
                if(address.isEmpty()){
                    binding.etAddress.error = "Enter your address"
                }
                if(password.isEmpty()){
                    binding.etPassword.error = "Enter your password"
                    binding.etPasswordLayout.isPasswordVisibilityToggleEnabled = false
                }
                if(confirmPwd.isEmpty()){
                    binding.etConfirmPwd.error = "Re-enter your password"
                    binding.etConfirmPwdLayout.isPasswordVisibilityToggleEnabled = false
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
                binding.signUpProgressbar.visibility = View.GONE
            }

            //validate email pattern
            else if (!email.matches(emailPattern.toRegex())){
                binding.etEmail.error = "Enter a valid email address"
                binding.signUpProgressbar.visibility = View.GONE
            }

            //validate phone number
            else if (phone.length != 10){
                binding.etPhone.error = "Enter a valid phone number"
                binding.signUpProgressbar.visibility = View.GONE
            }

            //validate passwords
            else if (password.length < 7){
                binding.etPassword.error = "Password must be at least 7 characters."
                binding.etPasswordLayout.isPasswordVisibilityToggleEnabled = false
                binding.signUpProgressbar.visibility = View.GONE
            }

            else if (confirmPwd != password){
                binding.etConfirmPwd.error = "Passwords do not match. Please try again."
                binding.etConfirmPwdLayout.isPasswordVisibilityToggleEnabled = false
                binding.signUpProgressbar.visibility = View.GONE
            }

            else{
                //creating a user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        val databaseRef = database.reference.child("users").child(auth.currentUser!!.uid)
                        val user: User = User(name, email, phone, address, auth.currentUser!!.uid)

                        databaseRef.setValue(user).addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(this, "Account created successfully.", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }
                            else{
                                Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }else{
                        Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}